package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.*;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.RecordSalesRecordService;
import com.kaishengit.crm.service.SalesService;
import com.kaishengit.crm.service.TaskService;
import com.kaishengit.util.StringsUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sales")
public class SalesContorller {

    @Autowired
    private SalesService salesService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RecordSalesRecordService recordSalesRecordService;

    @Autowired
    private TaskService taskService;

    /**
     * 查询进度列表和用户列表
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String salesNew(Model model){
        List<String> currents = salesService.findCurrent();
        List<Customer> customerList = salesService.findAllCustomer();
        model.addAttribute("currents",currents);
        model.addAttribute("customerList",customerList);
        return "sales/sales_new";
    }

    /**
     * 添加新销售记录
     * @param salesRecord
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/new")
    public String saveSales(@RequestParam(required = false) String content,SalesRecord salesRecord, RedirectAttributes redirectAttributes, HttpSession session){
        Account account = (Account) session.getAttribute("curr_user");
        salesService.saveSales(salesRecord,account,content);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/sales/my";
    }

    /**
     * 显示列表
     * @param model
     * @param pageNum
     * @param keyword
     * @param session
     * @return
     */
    @GetMapping("/my")
    public String salesMy(Model model,@RequestParam(required = false,defaultValue = "1",value = "p") Integer pageNum,
                          @RequestParam(required = false)String keyword,
                          HttpSession session){

        Map<String,Object> params = Maps.newHashMap();
        keyword =  StringsUtil.isoToUtf8(keyword);
        Account account = (Account) session.getAttribute("curr_user");
        Integer accountId = account.getId();
        params.put("keyword",keyword);
        params.put("accountId",accountId);
        params.put("pageNum",pageNum);

        PageInfo<SalesRecord> pageInfo = salesService.findAllSalesRecord(params);
        model.addAttribute("pageInfo",pageInfo);
        return "sales/sales_my";
    }

    /**
     * 查询客户资料，查询记录，查询跟进记录
     * @param id
     * @return
     */
    @GetMapping("/record/{id:\\d+}")
    public String showRecord(@PathVariable Integer id,HttpSession session,Model model){
        List<Task> taskList = taskService.findTaskBySalesId(id);
        //查找记录表
        SalesRecord salesRecord = salesService.findSalesRecordById(id);
        if(salesRecord == null) {
            throw new NotFoundException();
        }
        Account account = (Account) session.getAttribute("curr_user");
        if(!account.getId().equals(salesRecord.getAccountId())) {
            throw new ForbiddenException();
        }
        //查找记录列表
        List<RecordSalesRecord> recordSalesRecords = recordSalesRecordService.findBySalesRecordId(salesRecord.getId());
        //查找对应的customer用户
        Customer customer = customerService.findCustomerById(salesRecord.getCustomerId());
        //查询进度列表
        List<String> currents = salesService.findCurrent();
        salesRecord.setCustomer(customer);
        model.addAttribute("salesRecord",salesRecord);
        model.addAttribute("taskList",taskList);
        model.addAttribute("recordSalesRecords",recordSalesRecords);
        model.addAttribute("currents",currents);
        return "sales/sales_record";
    }

    /**
     * 修改当前进度
     * @return
     */
    @PostMapping("/curren/update")
    public String updateCurren(Integer id,String current){
        salesService.updateSales(id,current);
        return "redirect:/sales/record/" + id;
    }

    /**
     * 添加进度记录
     * @param recordSalesRecord
     * @return
     */
    @PostMapping("/new/record")
    public String newContent(RecordSalesRecord recordSalesRecord){
        recordSalesRecordService.saveRecord(recordSalesRecord);
        return "redirect:/sales/record/" + recordSalesRecord.getsalersrecordId();
    }

    /**
     * 删除销售机会
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/del/{id:\\d+}")
    public String delSales(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes){
        Account account = (Account) session.getAttribute("curr_user");
        SalesRecord salesRecord = salesService.findSalesRecordById(id);
        if(salesRecord == null) {
            throw new NotFoundException();
        }
        if(!salesRecord.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        salesService.delSalesRecordById(id);
        redirectAttributes.addFlashAttribute("message","删除机会成功");
        return "redirect:/sales/my";
    }


    @GetMapping("/new/{id:\\d+}")
    public String salesNewCustomer(@PathVariable Integer id,Model model){
        List<String> currents = salesService.findCurrent();
        Customer customer = customerService.findCustomerById(id);
        model.addAttribute("currents",currents);
        model.addAttribute("customer",customer);
        return "sales/sales_new";
    }

    @PostMapping("/newtask")
    public String newTask(Task task,HttpSession session){
        Account account = (Account) session.getAttribute("curr_user");
        task.setAccountId(account.getId());
        taskService.saveTask(task);
        return "redirect:/sales/record/" + task.getSalesRecordId();
    }


}
