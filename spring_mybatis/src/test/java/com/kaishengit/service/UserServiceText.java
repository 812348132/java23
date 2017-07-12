package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zjs on 2017/7/11.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceText {

    @Autowired
    private UserService userService;

    @Test
    public void save(){
        User user = new User();
        user.setUserName("qwe");
        user.setAddress("qwe");
        user.setPassword("123");
        user.setDeptId(1);

        userService.save(user);
    }

    @Test
    public void findByPage() {
        PageInfo<User> pageInfo = userService.findByPage(1, 5);
        List<User> userList = pageInfo.getList();
        for(User user : userList) {
            System.out.println(user);
        }
    }

}
