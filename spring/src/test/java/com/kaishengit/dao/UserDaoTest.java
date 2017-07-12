package com.kaishengit.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zjs on 2017/7/7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save(){

        //1.创建spring容器
        // 从classpath中读取spring的配置文件
       /* ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applictionContext.xml");*/
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applictionContext.xml");
        //2.从容器中获取被管理的Bean
        UserDao userDao = (UserDao) classPathXmlApplicationContext.getBean("userDao");
        UserDao userDao1 = (UserDao) classPathXmlApplicationContext.getBean("userDao2");

        classPathXmlApplicationContext.close();

    }

    @Test
    public void count(){
        Long count = userDao.count();
        Assert.assertEquals(count.longValue(),15L);
    }

}
