package com.kaishengit.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by zjs on 2017/7/19.
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
