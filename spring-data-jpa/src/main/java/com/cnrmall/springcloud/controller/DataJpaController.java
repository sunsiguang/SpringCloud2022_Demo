package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.dao.UserDao;
import com.cnrmall.springcloud.entity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2022/12/26 16:06
 */
@RestController
public class DataJpaController {

    @Autowired
    private UserDao userDao ;

    @PostMapping("/user")
    public  void  addUserDto() throws  Exception{

        UserDto user = new UserDto();
//        user.setId(2);
        user.setName("任我行");
        user.setAccount("renwox");
        user.setPwd("123456");
        userDao.save(user);
        user = new UserDto();
//        user.setId(4);
        user.setName("令狐冲");
        user.setAccount("linghuc");
        user.setPwd("123456");
        userDao.save(user);

    }

}
