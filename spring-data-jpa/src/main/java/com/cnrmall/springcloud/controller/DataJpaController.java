package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.dao.UserDao;
import com.cnrmall.springcloud.dao.UserRepository;
import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entity.UserDto;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * @author David
 * @date 2022/12/26 16:06
 *
 * 参考Demo: https://github.com/spring-projects/spring-data-examples/blob/main/jpa/query-by-example/src/test/java/example/springdata/jpa/querybyexample/UserRepositoryIntegrationTests.java
 *
 */
@RestController
public class DataJpaController {

    @Autowired
    private UserDao userDao ;

    @Autowired
    private UserRepository repository;

    @PostMapping("/user")
    public  void  addUserDto() throws  Exception{

        UserDto user = new UserDto("任我行","renwox", "123456");
//        user.setId(2);
        userDao.save(user);
        user = new UserDto("令狐冲","令狐冲", "123456");
        userDao.save(user);
    }

    @PostMapping("/saveAll")
    public void allBatchUserDto(){
        repository.deleteAll();

        UserDto skyler, walter, flynn, marie, hank;

        skyler = repository.save( new UserDto("Skyler","White","33"));  //使用部分参数构造方法
        walter = repository.save(new UserDto("Walter", "White","50"));
        flynn = repository.save(new UserDto("Walter Jr. (Flynn)", "White" ,"17"));
        marie = repository.save(new UserDto("Marie", "Schrader","38"));
        hank = repository.save( new UserDto("Hank", "Schrader" ,"43"));

    }

    /**
     * 匹配查询
     * @return
     */
    @GetMapping("/countByExample")
    public Long countBySimpleExample(){
        var example = Example.of(new UserDto( null, "White" ,null));

        return repository.count(example);
    }

    /**
     * 忽略查询
     * @return
     */
    @GetMapping("/ignoreCase")
    public CommonResult matchStartingStringsIgnoreCase(){
        var example = Example.of(new UserDto("Walter", "WHITE",  null), matching().
                withIgnorePaths("pwd").  //忽略 路径
                withMatcher("name", startsWith()).         //起始位置  Walter
                withMatcher("account", ignoreCase()));   // 忽略大小写 WHITE

        CommonResult commonResult = new CommonResult<>();
        List list = Collections.singletonList(repository.findAll(example));
        commonResult.setCode(200);
        commonResult.setData( list);
        return  commonResult;
    }

    /**
     * Optional是一个容器对象，可以包含也可以不包含非null值。
     * Optional在Java 8中引入，目的是解决  NullPointerExceptions的问题。本质上，Optional是一个包装器类，其中包含对其他对象的引用。
     * @return
     */
    @GetMapping("/findOne")
    public CommonResult matchObjectByPwd(){
        UserDto flynn =  new UserDto("Walter Jr. (Flynn)", "White", "33");
        var example = Example.of(flynn, matching(). //
                withIgnorePaths("name", "account"));  //忽略name 和 account
        Optional<UserDto> userDto = userDao.findOne(example);
        CommonResult commonResult = new CommonResult<>();
        commonResult.setCode(200);
        commonResult.setData(userDto);
        return  commonResult;
    }


    /**
     * substringMatching
     * @return
     */
    @GetMapping("/substring")
    public CommonResult substringMatching(){
        UserDto er =  new UserDto("er", null, null );
        var example = Example.of(er, matching(). //
                withStringMatcher(ExampleMatcher.StringMatcher.ENDING) );  //结尾匹配
        List<UserDto> userDtos = userDao.findAll(example);
        CommonResult commonResult = new CommonResult<>();
        commonResult.setCode(200);
        commonResult.setData(userDtos);
        return  commonResult;
    }

    @GetMapping("/valueTransformer")
    public CommonResult valueTransformer(){

        var example = Example.of(new UserDto("Walter", "White", "1199"), matching(). //
                withMatcher("pwd", matcher -> matcher.transform(value -> Optional.of("50"))));
        List<UserDto> userDtos = userDao.findAll(example);  // name , account ,  pwd 使用 transform的50 匹配查询
        CommonResult commonResult = new CommonResult<>();
        commonResult.setCode(200);
        commonResult.setData(userDtos);
        return  commonResult;
        //{"code":200,"message":null,"data":[{"id":21,"name":"Walter","account":"White","pwd":"50"}]}
    }





}
