package com.kaishengit.crm.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTableResult;
import com.kaishengit.dto.ZTreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    /**
     * 加载ZTree树
     * @return
     */
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



    /**
     * DataTables加载数据
     * @return
     */
    @GetMapping("/load.json")
    @ResponseBody
    public DataTableResult<Account> loadAccountData(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String deptId = request.getParameter("deptId");
        Integer id = null;
        if(StringUtils.isNotEmpty(deptId)) {
            id = Integer.valueOf(deptId);
        }
        //获取Account的总记录数
        Long total = accountService.countAll();
        //获取Account过滤后的数量
        Long filtedTotal = accountService.countByDeptId(id);
        //根据过滤的count查找对应的值
        //当前页的记录
        List<Account> accountList = accountService.findByDeptId(id);

        return new DataTableResult<>(draw,total,filtedTotal,accountList);
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @PostMapping("/del/{id:\\d+}")
    @ResponseBody
    public AjaxResult delAccountById(@PathVariable Integer id){
        accountService.delAccountById(id);
        return AjaxResult.success();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @PostMapping("/dept/del/{id:\\d+}")
    @ResponseBody
    public AjaxResult delDeptById(@PathVariable Integer id) {
        deptService.delDeptById(id);
        return AjaxResult.success();
    }

}
