package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.exception.ServiceException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by zjs on 2017/7/19.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 对象客户展示
     * @param pageNum
     * @param keyword
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String custMy(@RequestParam(required = false,defaultValue = "1",value = "p") Integer pageNum,
                         @RequestParam(required = false)String keyword,
                         HttpSession session,
                         Model model){
        Account account = (Account) session.getAttribute("curr_user");
        Integer accountId = account.getId();

        Map<String,Object> params = Maps.newHashMap();
        if(StringUtils.isNotEmpty(keyword)) {
            try{
                keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
                params.put("keyword",keyword);
            } catch (UnsupportedEncodingException e) {
                throw new ServiceException("编码异常");
            }
        }
        params.put("accountId",accountId);
        params.put("pageNum",pageNum);

        PageInfo<Customer> pageInfo = customerService.findMyCustomerByParam(params);
        model.addAttribute(pageInfo);
        return "cust/customer_my";
    }


    /**
     * 返回列表值
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String addCustomer(Model model){
        List<String> tradeList = customerService.findAllTrade();
        List<String> sourceList = customerService.findAllSource();
        model.addAttribute("tradeList",tradeList);
        model.addAttribute("sourceList",sourceList);
        return "cust/customer_new";
    }

    /**
     * 添加
     * @param customer
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/new")
    public String saveCustomer(Customer customer, HttpSession session, RedirectAttributes redirectAttributes){
        Account account = (Account) session.getAttribute("curr_user");
        customerService.saveCustomer(customer,account);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/customer/my";
    }

    /**
     * 客户详情
     * @return
     */
    @GetMapping("/info/{id:\\d+}")
    public String infoCustomer(@PathVariable Integer id,HttpSession session,Model model){
        //判断是否存在
        Customer customer = customerService.findCustomerById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否为用户的客户
        Account account = (Account) session.getAttribute("curr_user");
        if(!customer.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }

        model.addAttribute("customer",customer);
        return "cust/info";
    }

    /**
     * 显示客户信息
     * @return
     */
    @GetMapping("/edit/{id:\\d+}")
    public String editCustomer(@PathVariable Integer id, HttpSession session,Model model){
        //判断是否存在
        Customer customer = customerService.findCustomerById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否为用户的客户
        Account account = (Account) session.getAttribute("curr_user");
        if(!customer.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }

        model.addAttribute("customer",customer);
        List<String> tradeList = customerService.findAllTrade();
        List<String> sourceList = customerService.findAllSource();
        model.addAttribute("tradeList",tradeList);
        model.addAttribute("sourceList",sourceList);
        return "cust/customer_edit";
    }


    /**
     * 修改客户信息
     * @param customer
     * @param session
     * @return
     */
    @PostMapping("/edit/{id:\\d+}")
    public String updateCustomer(Customer customer,HttpSession session) {
        //判断是否存在
        Customer cust = customerService.findCustomerById(customer.getId());
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否为用户的客户
        Account account = (Account) session.getAttribute("curr_user");
        if(!customer.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }

        return "redirect:/customer/info/"+customer.getId();
    }

    /**
     * 删除客户
     * @return
     */
    @GetMapping("/del/{id:\\d+}")
    public String delCustomer(@PathVariable Integer id,HttpSession session){
        //判断是否存在
        Customer customer = customerService.findCustomerById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否为用户的客户
        Account account = (Account) session.getAttribute("curr_user");
        if(!customer.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }

        customerService.delCustomerById(id);

        return "redirect:/customer/my";
    }

}
