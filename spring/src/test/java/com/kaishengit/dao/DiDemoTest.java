package com.kaishengit.dao;

import com.kaishengit.service.DiDemo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zjs on 2017/7/7.
 */
public class DiDemoTest {

    @Test
    public void show(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applictionContext.xml");
        DiDemo diDemo = (DiDemo) applicationContext.getBean("diDemo");
        diDemo.show();
    }


}
