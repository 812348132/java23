package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zjs on 2017/7/13.
 */
@Controller
public class HomeController {

    @RequestMapping("/hello")
    public String save(){
        System.out.println("hello,springMvc");
        return "hello";
    }

}
