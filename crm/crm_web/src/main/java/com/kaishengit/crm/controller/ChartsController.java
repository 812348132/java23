package com.kaishengit.crm.controller;

import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.dto.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/charts")
public class ChartsController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/static")
    public String showCharts(){
        return "charts/static";
    }


    @GetMapping("/customer")
    public String customer(){
        return "charts/customer";
    }

    @GetMapping("/customer/bar.json")
    @ResponseBody
    public AjaxResult loadBarData() {
        List<Map<String,Object>> levelDataMap = customerService.findCustomerLevelCount();
        return AjaxResult.success(levelDataMap);
    }
}
