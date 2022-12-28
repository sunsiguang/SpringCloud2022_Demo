package com.cnrmall.springcloud.dao;

import com.cnrmall.springcloud.entity.User;
import com.cnrmall.springcloud.entity.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @author David
 * @date 2022/12/27 16:51
 */
@Repository
public interface SimpleUserRepository extends CrudRepository<User,Long> {

    /**
     * Find the user with the given username. This method will be translated into a query using the
     * {@link javax.persistence.NamedQuery} annotation at the {@link User} class.
     *
     * @param username
     * @return
     */
    User findByTheUsersName(String username);

    /**
     * Uses {@link Optional} as return and parameter type.
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(Optional<String> username);

    /**
     * Find all users with the given lastname. This method will be translated into a query by constructing it directly
     * from the method name as there is no other query declared.
     *
     * @param lastname
     * @return
     */
    List<User> findByLastname(String lastname);

    /**
     * Returns all users with the given firstname. This method will be translated into a query using the one declared in
     * the {@link Query} annotation declared one.
     *
     * @param firstname
     * @return
     */
    @Query("select u from User u where u.firstname = :firstname")
    List<User> findByFirstname(@Param("firstname") String firstname);

    /**
     * Returns all users with the given name as first- or lastname. This makes the query to method relation much more
     * refactoring-safe as the order of the method parameters is completely irrelevant.
     *
     * @param name
     * @return
     *
     * 当使用JPA的@Query来自定义查询，如果选择了参数名的绑定方式，
     * 当编译器级别大于等于1.8且添加了-parameters，则可以不加@Param，否则必须添加@Param
     */
    @Query("select u from User u where u.firstname = :name or u.lastname = :name")
    List<User> findByFirstnameOrLastname(@Param("name") String name);

    /**
     * Returns the total number of entries deleted as their lastnames match the given one.
     *
     * @param lastname
     * @return
     */
    Long removeByLastname(String lastname);

    /**
     * Returns a {@link Slice} counting a maximum number of {@link Pageable#getPageSize()} users matching given criteria
     * 返回一个计算匹配给定条件的用户的最大数量
     * starting at {@link Pageable#getOffset()} without prior count of the total number of elements available.
     *
     * @param lastname
     * @param page
     * @return
     */
    Slice<User> findByLastnameOrderByUsernameAsc(String lastname, Pageable page);

    Slice<User> findByLastnameOrderByUsernameDesc(String lastname, Pageable page);

    /**
     * Return the first 2 users ordered by their lastname asc.
     *
     * <pre>
     * Example for findFirstK / findTopK functionality.
     * </pre>
     *
     * @return
     */
    List<User> findFirst2ByOrderByLastnameAsc();

    /**
     * Return the first 2 users ordered by the given {@code sort} definition.
     *
     * <pre>
     * This variant is very flexible because one can ask for the first K results when a ASC ordering
     * is used as well as for the last K results when a DESC ordering is used.
     * </pre>
     *
     * @param sort
     * @return
     */
    List<User> findTop2By(Sort sort);

    /**
     * Return all the users with the given firstname or lastname. Makes use of SpEL (Spring Expression Language).
     *
     * @param user
     * @return
     */
    @Query("select u from User u where u.firstname = :#{#user.firstname} or u.lastname = :#{#user.lastname}")
    Iterable<User> findByFirstnameOrLastname(User user);

    /**
     * Sample default method.
     *
     * @param user
     * @return
     */
    default List<User> findByLastname(User user) {
        return findByLastname(user == null ? null : user.getLastname());
    }

    /**
     * Sample method to demonstrate support for {@link Stream} as a return type with a custom query. The query is executed
     * in a streaming fashion which means that the method returns as soon as the first results are ready.
     *
     * @return
     */
    @Query("select u from User u")
    Stream<User> streamAllCustomers();

    /**
     * Sample method to demonstrate support for {@link Stream} as a return type with a derived query. The query is
     * executed in a streaming fashion which means that the method returns as soon as the first results are ready.
     *
     * @return
     */
    Stream<User> findAllByLastnameIsNotNull();

    @Async  //异步查询结果
    CompletableFuture<List<User>> readAllBy();

    User findFirstByOrderByLastnameAsc();

    User findTopByOrderByLastnameAsc();
    Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);
    Slice<User> findTop3ByLastname(String lastname, Pageable pageable);
    List<User> findFirst10ByLastname(String lastname, Sort sort);
    List<User> findTop10ByLastname(String lastname, Pageable pageable);

}
