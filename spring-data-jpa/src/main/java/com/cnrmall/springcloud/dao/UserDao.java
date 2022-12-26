package com.cnrmall.springcloud.dao;

import com.cnrmall.springcloud.entity.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author David
 * @date 2022/12/26 16:03
 */
@Repository   //用户服务数据接口类
public interface UserDao extends JpaRepository<UserDto, Long> {
}
