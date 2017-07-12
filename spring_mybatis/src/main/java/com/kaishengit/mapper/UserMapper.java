package com.kaishengit.mapper;

import com.kaishengit.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zjs on 2017/7/11.
 */

public interface UserMapper {

    void save(User user);

    List<User> findAll();
}
