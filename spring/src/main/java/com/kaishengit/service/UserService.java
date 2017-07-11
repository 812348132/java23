package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.inject.Named;

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
    public void save(){
        userDao.save();
        /*if(true) {
            throw new RuntimeException("系统异常");
        }*/

    }


}
