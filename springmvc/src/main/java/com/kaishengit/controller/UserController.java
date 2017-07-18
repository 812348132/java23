package com.kaishengit.controller;

import com.kaishengit.entity.User;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zjs on 2017/7/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public String show(){
        System.out.println("user show");
        return "user/show";
    }

    @GetMapping("/{id:\\d+}")
    public String showId(@PathVariable Integer id){
        System.out.println(id);
        return "user/show";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showList(){
        return "user/show";
    }

    @GetMapping("/{className:java\\d+|web\\d+}/{name}")
    public String showUser(@PathVariable String className,@PathVariable String name){
        System.out.println(className + name);
        return "redirect:/show";
    }

    @GetMapping("/page")
    public String showPage(@RequestParam(required = false,defaultValue = "1") Integer page){
        System.out.println(page);
        return "redirect:/user/show";
    }

    @RequestMapping(value = "/api/show")
    @ResponseBody
    public User showUser(){
        User user = new User();
        user.setId(123);
        user.setUserName("jack");
        user.setAddress("北京");
        return user;
    }

    @GetMapping("/api/all")
    @ResponseBody
    public List<User> showAll(){
        User user = new User();
        user.setId(123);
        user.setUserName("jack");
        user.setAddress("北京");

        User user1 = new User();
        user1.setId(1232);
        user1.setUserName("jack1");
        user1.setAddress("北京1");

        List<User> userList = Arrays.asList(user,user1);
        return userList;
    }

   /* @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("name","李四");
        return "user/list";
    }*/

    @GetMapping("/save")
    public ModelAndView list(){
        ModelAndView model = new ModelAndView();
        model.setViewName("user/list");
        model.addObject("name","李四");
        return model;
    }

    @PostMapping("/save")
    public String save(User user, String score, RedirectAttributes redirectAttributes){
        System.out.println(user.getUserName());
        System.out.println(user.getAddress());
        System.out.println(score);

        redirectAttributes.addFlashAttribute("message","操作成功");

        return "redirect:/user/save";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(MultipartFile docName){
        System.out.println(docName.getOriginalFilename());
        System.out.println(docName.getSize());
        System.out.println(docName.getContentType());

        try{
            InputStream inputStream = docName.getInputStream();
            OutputStream outputStream = new FileOutputStream(new File("F:/upload",docName.getOriginalFilename()));
            IOUtils.copy(inputStream,outputStream);

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        return "redirect:/user/save";
    }


}
