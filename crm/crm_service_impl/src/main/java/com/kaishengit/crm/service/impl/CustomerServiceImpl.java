package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.RecordSalesRecord;
import com.kaishengit.crm.entity.SalesRecord;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.RecordSalesRecordMapper;
import com.kaishengit.crm.mapper.SalesMapper;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.weixin.WeiXinUtil;
import com.kaishengit.exception.ServiceException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zjs on 2017/7/20.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Value("#{'${customer.trade}'.split(',')}") //SpringEL
    private List<String> tradeList;
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> sourceList;

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Autowired
    private CustomerMapper customerMapper;
   /* @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private RecordSalesRecordMapper recordSalesRecordMapper;*/

    @Override
    public List<String> findAllTrade() {
        return tradeList;
    }

    @Override
    public List<String> findAllSource() {
        return sourceList;
    }

    @Override
    public void saveCustomer(Customer customer, Account account) {
        customer.setAccountId(account.getId());
        customer.setCreateTime(new Date());
        customerMapper.saveCustomer(customer);

        weiXinUtil.sendTextMessageToUser("新增客户[ "+ customer.getCustName() +" ]","ZhaoJinShuai");
    }

    @Override
    public PageInfo<Customer> findMyCustomerByParam(Map<String, Object> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        PageHelper.startPage(pageNum,10);
        List<Customer> customers = customerMapper.findMyCustomerByParams(params);
        return new PageInfo<>(customers);
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerMapper.findCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateCustomer(customer);
    }

    @Override
    @Transactional
    public void delCustomerById(Integer id) {

        //TODO 删除日程安排
         /*Customer customer = customerMapper.findCustomerById(id);
        List<SalesRecord> salesRecords = salesMapper.findAllByCustomerId(customer.getId());
        for (SalesRecord salesRecord : salesRecords) {
            recordSalesRecordMapper.delBySalesRecordId(salesRecord.getId());
        }*/
        //TODO 删除销售机会
       /* salesMapper.delSalesRecoredByCustomerId(customer.getId());*/


        //TODO 删除相关资料
        //删除客户
        customerMapper.delCustomerById(id);
    }

    @Override
    public void updateCustomerToPublic(Customer customer,Account account) {
        customer.setUpdateTime(new Date());
        customer.setAccountId(null);
        customer.setReminder(account.getUserName() +"将" + customer.getCustName()+"放入公海");
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void transferToAccount(Customer customer, Account newAccount,Account account) {
        customer.setUpdateTime(new Date());
        customer.setAccountId(newAccount.getId());
        customer.setReminder("由"+account.getUserName()+"转交");
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void customerToExcel(Account account, OutputStream outputStream) {

        List<Customer> customerList = customerMapper.findCustomerByAccountId(account.getId());
        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创建sheet页签
        Sheet sheet = workbook.createSheet("客户信息表");
        //创建数据
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("客户名称");
        row.createCell(1).setCellValue("职位");
        row.createCell(2).setCellValue("级别");
        row.createCell(3).setCellValue("联细方式");

        for(int i = 0;i <customerList.size();i++) {
            Customer customer = customerList.get(i);
            Row rowCustomer = sheet.createRow(i+1);
            rowCustomer.createCell(0).setCellValue(customer.getCustName());
            rowCustomer.createCell(1).setCellValue(customer.getJobTitle());
            rowCustomer.createCell(2).setCellValue(customer.getLevel());
            rowCustomer.createCell(3).setCellValue(customer.getMobile());
        }

        //将工作表写到磁盘上
        try{
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new ServiceException("导出excel异常",e);
        }
    }

    @Override
    public void transferMy(Customer customer, Account account) {
        customer.setUpdateTime(new Date());
        customer.setAccountId(account.getId());
        customer.setReminder("由公海加入");
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void savePublicCustomer(Customer customer) {
        customer.setCreateTime(new Date());
        customer.setAccountId(null);
        customerMapper.saveCustomer(customer);
    }

    @Override
    public void customerPublicToExcel(OutputStream outputStream) {
        List<Customer> customerList = customerMapper.findCustomerByAccountId(null);
        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创建sheet页签
        Sheet sheet = workbook.createSheet("公海客户表");
        //创建数据
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("客户姓名");
        row.createCell(1).setCellValue("职位");
        row.createCell(2).setCellValue("级别");
        row.createCell(3).setCellValue("联细方式");

        for (int i = 0;i < customerList.size();i++){
            Row row1 = sheet.createRow(i+1);
            Customer customer = new Customer();
            customer = customerList.get(i);
            row1.createCell(0).setCellValue(customer.getCustName());
            row1.createCell(1).setCellValue(customer.getJobTitle());
            row1.createCell(2).setCellValue(customer.getLevel());
            row1.createCell(3).setCellValue(customer.getMobile());
        }
        //将工作表写到磁盘上

        try{
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new ServiceException("导出失败");
        }
    }

    @Override
    public List<Map<String, Object>> findCustomerLevelCount() {
        return customerMapper.findCostomerLevelCount();
    }
}
