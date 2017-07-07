package com.kaishengit.service;

import com.kaishengit.dao.UserDao;

/**
 * Created by zjs on 2017/7/7.
 */
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    /*public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }*/

    public void save(){
        userDao.save();
    }


}
