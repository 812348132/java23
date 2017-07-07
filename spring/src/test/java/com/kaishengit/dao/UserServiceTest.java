package com.kaishengit.dao;

import com.kaishengit.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zjs on 2017/7/7.
 */
public class UserServiceTest {

    @Test
    public void save(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applictionContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");

        userService.save();

    }
}
