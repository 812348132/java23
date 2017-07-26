package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskContorller {

    @Autowired
    private TaskService taskService;


    /**
     * 待办任务首页
     * @return
     */
    @GetMapping("/my")
    public String showTask(@RequestParam(required = false,defaultValue = "")String show, Model model, HttpSession session){
        Account account = (Account) session.getAttribute("curr_user");
        boolean showAll = "all".equals(show) ? true : false;
        List<Task> taskList = taskService.findAllTaskByAccountId(account.getId(),showAll);
        model.addAttribute("taskList",taskList);
        return "task/task_my";
    }

    /**
     * 新增代办页面
     * @return
     */
    @GetMapping("/new")
    public String newTask(){
        return "task/task_new";
    }

    /**
     * 新增待办事务
     * @param task
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/new")
    public String saveTask(Task task, HttpSession session, RedirectAttributes redirectAttributes){
        Account account = (Account) session.getAttribute("curr_user");
        task.setAccountId(account.getId());
        taskService.saveTask(task);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/task/my";
    }


    /**
     * 修改待办事项的状态 已完成 | 未完成
     */
    @GetMapping("/{id:\\d+}/state/{state}")
    public String changeTaskState(@PathVariable Integer id, @PathVariable String state, HttpSession session) {
        Task task = taskService.findAllTaskById(id);
        Account account = (Account) session.getAttribute("curr_user");

        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //根据state变量决定对象的状态
        if("done".equals(state)) {
            task.setDone(true);
        } else {
            task.setDone(false);
        }

        taskService.updateTask(task);
        return "redirect:/task/my";
    }

    /**
     * 删除待办任务
     */
    @GetMapping("/del/{id:\\d+}")
    public String delTask(@PathVariable Integer id,HttpSession session) {
        Task task = taskService.findTaskById(id);
        Account account = (Account) session.getAttribute("curr_user");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }
        taskService.delTask(task);
        return "redirect:/task/my";
    }

    /**
     * 修改代办任务
     * */
    @GetMapping("/update/{id:\\d+}")
    public String updateTask(@PathVariable Integer id,HttpSession session,Model model) {
        Task task = taskService.findTaskById(id);
        Account account = (Account) session.getAttribute("curr_user");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }
        model.addAttribute("task",task);
        return "task/task_update";
    }


    /**
     * 修改代办任务
     * */
    @PostMapping("/update")
    public String updateTask(Task task,HttpSession session) {
        Account account = (Account) session.getAttribute("curr_user");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }
        taskService.updateTask(task);
        return "redirect:/task/my";
    }


}
