package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Dept;

import java.util.List;

/**
 * Created by zjs on 2017/7/17.
 */
public interface DeptService {


    List<Dept> findAll();

    void save(Dept dept);
}
