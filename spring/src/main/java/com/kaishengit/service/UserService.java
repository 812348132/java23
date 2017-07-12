package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * Created by zjs on 2017/7/7.
 */

@Named
public class UserService {
    @Autowired
    private UserDao userDao;

    /*public UserService(UserDao userDao){
        this.userDao = userDao;
    }*/

    /*public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }*/
    @Transactional(rollbackFor = Exception.class)
    public void save(){
        User user = new User();
        user.setUser_name("jack");
        user.setAddress("北京");
        user.setPassword("123123");
        user.setDept_id(1);
        userDao.save(user);
        /*if(true) {
            throw new RuntimeException("系统异常");
        }*/

    }

    public void findById(){
        User user = userDao.findById(3);
        System.out.println(user.getUser_name() + "-->" +user.getAddress());
    }


    public void findAll(){
        List<User> lit = userDao.findAll();
        for(User user : lit) {
            System.out.println(user.getUser_name() + "-->" +user.getAddress());
        }
    }


}
