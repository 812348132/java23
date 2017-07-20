package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.AuthenticationException;
import com.kaishengit.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by zjs on 2017/7/19.
 */
@Controller
public class LogingController {

    @Autowired
    private AccountService accountService;

    /**
     * 去登录页面
     * @return
     */
    @GetMapping("/")
    public String login(@RequestParam(required = false) String callback, Model model){
        model.addAttribute("callback",callback);
        return "login";
    }
    /**
     * 登录判断
     * @param mobile
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public AjaxResult login(String mobile, String password, HttpSession session){
        try{
            Account account = accountService.login(mobile,password);
            session.setAttribute("curr_user",account);
            return AjaxResult.success();
        } catch (AuthenticationException ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("message","你已安全退出");
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(){
        return "manage/profile";
    }

    /**
     * 修改密码
     * @return
     */
    @PostMapping("/change")
    @ResponseBody
    public AjaxResult changePassword(String oldPassword, String password, HttpSession session){
        Account account = (Account) session.getAttribute("curr_user");
        try{
            accountService.changePassword(oldPassword,password,account);
            session.invalidate();
            return AjaxResult.success();
        } catch (ServiceException e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
