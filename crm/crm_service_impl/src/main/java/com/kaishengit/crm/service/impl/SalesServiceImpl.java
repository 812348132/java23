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
import com.kaishengit.crm.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SalesServiceImpl implements SalesService {

    @Value("#{'${sales.current}'.split('/')}")
    private List<String> currents;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private RecordSalesRecordMapper recordSalesRecordMapper;


    /**
     * 查询所有跟进记录
     * @return
     */
    @Override
    public List<String> findCurrent() {
        return currents;
    }

    /**
     * 查询所有客户
     * @return
     */
    @Override
    public List<Customer> findAllCustomer() {
        return customerMapper.findAllCustomer();
    }

    /**
     * 新增销售记录
     * @param salesRecord
     */
    @Override
    @Transactional
    public void saveSales(SalesRecord salesRecord,Account account,String content) {
        //增加一条记录
        salesRecord.setCreateTime(new Date());
        salesRecord.setFollowTime(new Date());
        salesRecord.setAccountId(account.getId());
        salesMapper.saveSales(salesRecord);
        //增加一条详细内容
        RecordSalesRecord recordSalesRecord = new RecordSalesRecord();
        recordSalesRecord.setContent(content);
        recordSalesRecord.setSalersrecordId(salesRecord.getId());
        recordSalesRecord.setCreateTime(new Date());
        recordSalesRecordMapper.saveRecord(recordSalesRecord);
        //修改客户跟进时间
        Integer custId = salesRecord.getCustomerId();
        Customer customer = customerMapper.findCustomerById(custId);
        customer.setLastContactTime(new Date());
        customerMapper.updateCustomer(customer);
    }

    /**
     * 显示列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<SalesRecord> findAllSalesRecord(Map<String, Object> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        PageHelper.startPage(pageNum,10);
        List<SalesRecord> salesRecords = salesMapper.findAll(params);
        return new PageInfo<>(salesRecords);
    }


    @Override
    public SalesRecord findSalesRecordById(Integer id) {
        return salesMapper.findById(id);
    }

    /**
     * 修改当前进度
     * @param id
     * @param current
     */
    @Override
    @Transactional
    public void updateSales(Integer id, String current) {
        //修改进度
        SalesRecord salesRecord = salesMapper.findById(id);
        salesRecord.setCurrent(current);
        salesMapper.updateSales(salesRecord);
        //添加跟进记录
        RecordSalesRecord record = new RecordSalesRecord();
        record.setCreateTime(new Date());
        record.setSalersrecordId(id);
        record.setContent("将当前进度修改为：[ " + current + " ]");
        recordSalesRecordMapper.saveRecord(record);
    }

    /**
     * 删除销售机会
     * @param id
     */
    @Override
    @Transactional
    public void delSalesRecordById(Integer id) {
        //删除跟进记录
        recordSalesRecordMapper.delBySalesRecordId(id);
        //TODO 删除文档
        //TODO 删除任务
        //删除机会
        salesMapper.delSalesRecoredById(id);
    }

    /**
     * 查找对应客户的销售机会
     * @param id
     * @return
     */
    @Override
    public List<SalesRecord> findAllSalesRecordByCustomerId(Integer id) {
        return salesMapper.findAllByCustomerId(id);
    }


}
