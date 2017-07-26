package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.*;
import com.kaishengit.crm.service.*;
import com.kaishengit.util.QRCardUtil;
import com.kaishengit.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    @Autowired
    private AccountService accountService;
    @Autowired
    private RecordSalesRecordService recordSalesRecordService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private TaskService taskService;

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

        keyword =  StringsUtil.isoToUtf8(keyword);

        params.put("keyword",keyword);
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
     * 用户客户详情
     * @return
     */
    @GetMapping("/info/{id:\\d+}")
    public String infoCustomer(@PathVariable Integer id,HttpSession session,Model model){
        //查询待办任务
        List<Task> taskList = taskService.findTaskByCustId(id);
        //查找销售机会
        List<SalesRecord> salesRecordList = salesService.findAllSalesRecordByCustomerId(id);
        //查找所有的Account用户
        List<Account> accounts = accountService.findAllAccount();
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
        model.addAttribute("salesRecordList",salesRecordList);
        model.addAttribute("taskList",taskList);
        model.addAttribute("customer",customer);
        model.addAttribute("accountList",accounts);
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
    public String updateCustomer(Customer customer,HttpSession session,RedirectAttributes redirectAttributes) {
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

        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/customer/info/"+customer.getId();
    }

    /**
     * 删除客户
     * @return
     */
    @GetMapping("/del/{id:\\d+}")
    public String delCustomer(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes){
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
        redirectAttributes.addFlashAttribute("message","删除成功");

        return "redirect:/customer/my";
    }

    /*转入公海*/
    @GetMapping("/transferpubuic/{id:\\d+}")
    public String transferpubuic(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes){
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

        customerService.updateCustomerToPublic(customer,account);

        redirectAttributes.addFlashAttribute("message","将" +customer.getCustName()+"放入公海");
        return "redirect:/customer/my";
    }

    /**
     * 转交给其他人客户
     * @param custId
     * @param accountId
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/{custId:\\d+}/tran/{accountId:\\d+}")
    public String transferToAccount(@PathVariable Integer custId,@PathVariable Integer accountId,
                                    HttpSession session,RedirectAttributes redirectAttributes){
        //判断是否存在
        Customer customer = customerService.findCustomerById(custId);
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否为用户的客户
        Account account = (Account) session.getAttribute("curr_user");
        if(!customer.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }

        Account newAccount = accountService.findAccountById(accountId);
        customerService.transferToAccount(customer,newAccount,account);

        redirectAttributes.addFlashAttribute("message","成功将"+customer.getCustName()+"转交给"+newAccount.getUserName());
        return "redirect:/customer/my";
    }


    /**
     * 将客户导出为excel
     */
    @GetMapping("/export")
    public void export(HttpSession session, HttpServletResponse response) throws Exception{
        Account account = (Account) session.getAttribute("curr_user");
        //告诉浏览器输出内容的MIME
        response.setContentType("application/vnd.ms-excel");
        //设置弹出对话框的文件名称(要进行转移)
        response.addHeader("Content-Disposition","attachment;filename=\"customer.xls\"");
        customerService.customerToExcel(account,response.getOutputStream());
    }

    /**
     * 将公海客户导出为excel
     */
    @GetMapping("/public/export")
    public void publicExport(HttpServletResponse response) throws Exception{
        //告诉浏览器输出内容的MIME
        response.setContentType("application/vnd.ms-excel");
        //设置弹出对话框的文件名称(要进行转移)
        response.addHeader("Content-Disposition","attachment;filename=\"customer.xls\"");
        customerService.customerPublicToExcel(response.getOutputStream());
    }


    /**
     * 公海客户展示
     * @param pageNum
     * @param keyword
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/public")
    public String custPublic(@RequestParam(required = false,defaultValue = "1",value = "p") Integer pageNum,
                         @RequestParam(required = false)String keyword,
                         HttpSession session,
                         Model model){

        Map<String,Object> params = Maps.newHashMap();
        keyword =  StringsUtil.isoToUtf8(keyword);
        params.put("keyword",keyword);
        params.put("accountId",null);
        params.put("pageNum",pageNum);

        PageInfo<Customer> pageInfo = customerService.findMyCustomerByParam(params);
        model.addAttribute(pageInfo);
        return "cust/customer_public";
    }

    /**
     * 公海客户详情
     * @param id
     * @return
     */
    @GetMapping("/info/public/{id:\\d+}")
    public String customerPublic(@PathVariable Integer id,Model model){
        //查询此公海客户
        Customer customer = customerService.findCustomerById(id);
        model.addAttribute("customer",customer);
        return "cust/info_public";
    }

    /**
     * 抢人
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/public/transfermy/{id:\\d+}")
    public String transferMy(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes){
        //判断是否存在
        Customer customer = customerService.findCustomerById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        Account account = (Account) session.getAttribute("curr_user");
        customerService.transferMy(customer,account);
        redirectAttributes.addFlashAttribute("message","抢人成功");
        return "redirect:/customer/my";
    }


    /**
     * 添加公海客户
     * @param model
     * @return
     */
    @GetMapping("/public/new")
    public String addPublicCustomer(Model model){
        List<String> tradeList = customerService.findAllTrade();
        List<String> sourceList = customerService.findAllSource();
        model.addAttribute("tradeList",tradeList);
        model.addAttribute("sourceList",sourceList);
        return "cust/customer_new";
    }

    /**
     * 添加公海客户
     * @param customer
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/public/new")
    public String savePublicCustomer(Customer customer, RedirectAttributes redirectAttributes){
        customerService.savePublicCustomer(customer);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/customer/public";
    }

    /**
     * 生成二维码图片
     * @param id
     * @param response
     */
    @GetMapping("/qrcode/{id:\\d+}")
    public void showCustomerQRCode(@PathVariable Integer id,HttpServletResponse response){
        //查询对应的用户
        Customer customer = customerService.findCustomerById(id);
        //设置响应头
        response.setContentType("image/png");
        //设置Vcard格式 https://zxing.appspot.com/generator
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("BEGIN:VCARD\r\n");
        stringBuffer.append("VERSION:3.0\r\n");
        stringBuffer.append("N:").append(customer.getCustName()).append("\r\n");
        stringBuffer.append("TITLE:").append(customer.getJobTitle()).append("\r\n");
        stringBuffer.append("TEL:").append(customer.getMobile()).append("\r\n");
        stringBuffer.append("ADR:").append(customer.getAddress()).append("\r\n");
        stringBuffer.append("END:VCARD\r\n");

        try{
            QRCardUtil.writeToStream(stringBuffer.toString(),response.getOutputStream(),300,300);
        } catch (IOException | WriterException e){
            throw new RuntimeException("加载失败");
        }
    }

    /**
     * 新增代办任务
     * @param task
     * @param session
     * @return
     */
    @PostMapping("/newtask")
    public String newTask(Task task,HttpSession session){
        Account account = (Account) session.getAttribute("curr_user");
        task.setAccountId(account.getId());
        taskService.saveTask(task);
        return "redirect:/customer/info/" + task.getCustomerId();
    }


}
