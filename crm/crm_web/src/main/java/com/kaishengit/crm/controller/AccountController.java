package com.kaishengit.crm.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.ZTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjs on 2017/7/17.
 */
@Controller
@RequestMapping("/manage/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DeptService deptService;

    @GetMapping("/accounts")
    @ResponseBody
    public List<Account> findAllAccount(){
       return accountService.findAllAccount();
    }

    @GetMapping
    public String accountList(){
        return "manage/accounts";
    }

    @PostMapping("/depts.json")
    @ResponseBody
    public List<ZTreeNode> findAllDept(){
        List<Dept> deptList = deptService.findAll();

        List<ZTreeNode> zTreeNodes = Lists.newArrayList(Collections2.transform(deptList, new Function<Dept, ZTreeNode>() {
            @Nullable
            @Override
            public ZTreeNode apply(@Nullable Dept dept) {
                ZTreeNode zTreeNode = new ZTreeNode();
                zTreeNode.setId(dept.getId());
                zTreeNode.setName(dept.getDeptName());
                zTreeNode.setpId(dept.getpId());
                return zTreeNode;
            }
        }));

        return zTreeNodes;
        /*List<ZTreeNode> zTreeNodes = new ArrayList<>();
        for (Dept dept : deptList) {
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(dept.getId());
            zTreeNode.setName(dept.getDeptName());
            zTreeNode.setpId(dept.getpId());
            zTreeNodes.add(zTreeNode);
        }
        return  zTreeNodes;*/
    }


    @PostMapping("/dept/new")
    @ResponseBody
    public AjaxResult saveNewDept(Dept dept){
        deptService.save(dept);
        return AjaxResult.success();
    }

    @PostMapping("/new")
    @ResponseBody
    public AjaxResult saveAccount(Account account,Integer[] deptId){
        accountService.saveAccount(account,deptId);
        return AjaxResult.success();
    }

}