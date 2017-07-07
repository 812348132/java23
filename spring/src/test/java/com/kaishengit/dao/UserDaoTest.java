package com.kaishengit.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zjs on 2017/7/7.
 */
public class UserDaoTest {


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

}
