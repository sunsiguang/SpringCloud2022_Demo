package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.dao.SimpleUserRepository;
import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.domain.Sort.Direction.ASC;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author David
 * @date 2022/12/27 17:25
 *
 * 参考： https://github.com/spring-projects/spring-data-examples/blob/main/jpa/example/src/test/java/example/springdata/jpa/simple/SimpleUserRepositoryTests.java
 */
@RestController
@Slf4j
public class SimpleUserRepositroyController {

    @Autowired
    SimpleUserRepository repository;
    private User user;

    @PostConstruct
    void setUp(){
        user = new User();
        user.setUsername("foobar");
        user.setFirstname("firstname");
        user.setLastname("lastname");
    }

    @PostMapping("/simple/adduser")
    public void addUser(){
        user = new User();
        user.setUsername("foobar");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user = repository.save(user);
    }

    @GetMapping("/simple/findById/{id}")
    public CommonResult findById(@PathVariable("id")  Long id  ){
        Optional<User> u = repository.findById(id);
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( u );
        return  commonResult;
    }

    @GetMapping("/simple/findByName/{name}")
    public CommonResult findBName(@PathVariable("name")  String name  ){
        List<User>  users = repository.findByFirstnameOrLastname(name);
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }

    //按username , 这里的页数从零开始, 1 是第二页开始，查询 5条
    //interface Page<T> extends Slice<T>  Page是继承了Slice, ,分页和排序使用
    @GetMapping("/simple/useSlice")
    public CommonResult useSliceToLoadContent(){

//        repository.deleteAll();
//        // int repository with some values that can be ordered
//        int totalNumberUsers = 11;
//        List<User> source = new ArrayList<>(totalNumberUsers);
//
//        for (int i = 1; i <= totalNumberUsers; i++) {
//
//            User user = new User();
//            user.setLastname(this.user.getLastname());
//            user.setUsername(user.getLastname() + "-" + String.format("%03d", i));
//            source.add(user);
//        }
//        repository.saveAll(source);

        // Springdata中的Streamable可以替代list、Iterable、Set等最为返回类型,Streamable和Stream不同，不用关闭资源
        // interface Page<T> extends Slice<T>  Page是继承了Slice, ,分页和排序使用
        // 按username , 这里的页数从零开始, 1 是第二页开始，查询 5条
        Slice<User> users = repository.findByLastnameOrderByUsernameAsc(this.user.getLastname(), PageRequest.of(1, 5));
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }

    @GetMapping("/simple/useSliceDesc")
    public CommonResult useSliceToLoadContentDesc(){
        Slice<User> users = repository.findByLastnameOrderByUsernameDesc(this.user.getLastname(), PageRequest.of(1, 5));
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }


    /**
     * 查询最大结果，按LastName 升序排序
     * 查询方法的结果可以通过关键字来限制 first 或 top，其可以被可互换使用，可选的数值可以追加到顶部/第一个以指定要返回的最大结果的大小
     *
     * @return
     */
    @GetMapping("/simple/findFirst")
    public CommonResult findFirstByOrderByLastnameAsc(){
        User first = repository.findFirstByOrderByLastnameAsc();
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( first );
        return  commonResult;
    }

    /**
     * 查询最大结果，按LastName 升序排序
     * @return
     */
    @GetMapping("/simple/findTop")  // == findFirstByOrderByLastnameAsc
    public CommonResult findTopByOrderByLastnameAsc(){
        User first = repository.findTopByOrderByLastnameAsc();
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( first );
        return  commonResult;
    }

    /**
     * 前10个，按LastName 排序
     *
     * select
     *         user0_.id as id1_0_,
     *         user0_.firstname as firstnam2_0_,
     *         user0_.lastname as lastname3_0_,
     *         user0_.username as username4_0_
     *     from
     *         user user0_
     *     where
     *         user0_.lastname=?
     *     order by
     *         user0_.lastname asc limit ?     *
     * @return
     */
    @GetMapping("/simple/findFirst10")  // == findFirstByOrderByLastnameAsc
    public CommonResult findFirst10ByLastname(){
        List<User> users = repository.findFirst10ByLastname(this.user.getLastname(), Sort.by(ASC, "lastname"));  // order by  列
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }


    /**
     * 查询前2个 ，根据 column ，按 sort 排序
     *
     * http://localhost:88/simple/findTop2/username/DESC
     *  select
     *         user0_.id as id1_0_,
     *         user0_.firstname as firstnam2_0_,
     *         user0_.lastname as lastname3_0_,
     *         user0_.username as username4_0_
     *     from
     *         user user0_
     *     order by
     *         user0_.username desc limit ?
     *
     * http://localhost:88/simple/findTop2/username/ASC
     * select
     *         user0_.id as id1_0_,
     *         user0_.firstname as firstnam2_0_,
     *         user0_.lastname as lastname3_0_,
     *         user0_.username as username4_0_
     *     from
     *         user user0_
     *     order by
     *         user0_.username asc limit ?
     *
     * @param column
     * @param sort
     * @return
     */
    @GetMapping("/simple/findTop2/{column}/{sort}")
    public CommonResult findTop2ByWithSort(@PathVariable("column") String column , @PathVariable("sort") Sort.Direction sort ){
        List<User> users = repository.findTop2By(Sort.by(sort, column));//按lastname 升序排序，查询 前2 个
        //repository.findTop2By(Sort.by(ASC, "lastname"));
        //repository.findTop2By(Sort.by(DESC, "username"));
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }

    /**
     * Streams
     *
     * http://localhost:88/simple/streamsQuery     *
     * select
     *         user0_.id as id1_0_,
     *         user0_.firstname as firstnam2_0_,
     *         user0_.lastname as lastname3_0_,
     *         user0_.username as username4_0_
     *     from
     *         user user0_
     *
     * @return
     */
    @GetMapping("/simple/streamsQuery")
    @Transactional   //org.springframework.dao.InvalidDataAccessApiUsageException: You're trying to execute a streaming query method without a surrounding transaction that keeps the connection open so that the Stream can actually be consumed. Make sure the code consuming the stream uses @Transactional or any other way of declaring a (read-only) transaction.
    public CommonResult useJava8StreamsWithCustomQuery(){
        Stream<User> allCustomers = repository.streamAllCustomers();
        List<User> users = allCustomers.collect(Collectors.toList());
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }

    /**
     * http://localhost:88/simple/future
     * select
     *         user0_.id as id1_0_,
     *         user0_.firstname as firstnam2_0_,
     *         user0_.lastname as lastname3_0_,
     *         user0_.username as username4_0_
     *     from
     *         user user0_
     * @return
     * @throws Exception
     */
    @GetMapping("/simple/future")
    @Transactional( propagation = Propagation.NOT_SUPPORTED)
    public CommonResult  supportsCompletableFuturesAsReturnTypeWrapper() throws Exception{

        //无返回值
//        CompletableFuture<Void> future = repository.readAllBy().thenAccept(users -> {
//            users.forEach(customer -> log.info(customer.toString()));
//            log.info("Completed!");
//        });

        //有返回值
        CompletableFuture<List<User>> future = repository.readAllBy();
        while (!future.isDone() ){
            // 等待执行完
            log.info("Waiting for the CompletableFuture to finish...");
            TimeUnit.SECONDS.sleep(5);
        }
        List<User> users = future.get();//获取查询结果
        log.info("Done!");
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData( users );
        return  commonResult;
    }
}
