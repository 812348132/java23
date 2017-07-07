package com.kaishengit.service;

import com.kaishengit.dao.UserDao;

import java.util.*;

/**
 * Created by zjs on 2017/7/7.
 */
public class DiDemo {

    private double score;
    private String name;
    private List<String> userList;
    private Set<String> userSet;
    private Map<String,UserDao> userMap;
    private Properties config;

    public void show(){

        System.out.println(score);
        System.out.println(name);

        for (String str : userList) {
            System.out.println(str);
        }

        for (String str : userSet) {
            System.out.println(str);
        }

        for (Map.Entry<String,UserDao> entry : userMap.entrySet()) {
            System.out.println(entry.getKey() +"-->"+entry.getValue());
        }

        Enumeration keys = config.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = config.getProperty(key);
            System.out.println(key +"-->" + value);
        }

    }


    public void setScore(double score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public void setUserSet(Set<String> userSet) {
        this.userSet = userSet;
    }

    public void setUserMap(Map<String, UserDao> userMap) {
        this.userMap = userMap;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }


}
