package com.kaishengit.dao;

import com.kaishengit.Application;
import com.kaishengit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by zjs on 2017/7/7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void save(){
        /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        UserService userService = (UserService) applicationContext.getBean("userService");*/
        userService.save();
    }
}
