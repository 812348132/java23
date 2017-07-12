package com.kaishengit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zjs on 2017/7/11.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void save(User user){
        userMapper.save(user);
    }

    public PageInfo<User> findByPage(int pageNo,int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<User> userList = userMapper.findAll();
        return new PageInfo<User>(userList);
    }
}
