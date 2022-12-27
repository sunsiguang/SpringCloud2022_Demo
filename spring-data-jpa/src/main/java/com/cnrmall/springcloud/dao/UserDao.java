package com.cnrmall.springcloud.dao;

import com.cnrmall.springcloud.entity.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author David
 * @date 2022/12/26 16:03
 *  在下图中的 Repository、CrudRepository 和 PagingAndSortingRepository
 *  属于 Spring Data Commons 而 JpaRepository 属于 Spring Data JPA。
 *
 *   JpaRepository 是 Repository 的JPA（Java Persistence API）特定扩展。
 *   它包含CrudRepository和PagingAndSortingRepository的完整 API 。因此它包含用于基本 CRUD 操作的 API 以及用于分页和排序的 API
 */
@Repository   //用户服务数据接口类
public interface UserDao extends JpaRepository<UserDto, Long> {
}
