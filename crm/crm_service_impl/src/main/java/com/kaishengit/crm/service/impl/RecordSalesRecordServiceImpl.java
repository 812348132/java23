package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.RecordSalesRecord;
import com.kaishengit.crm.entity.SalesRecord;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.RecordSalesRecordMapper;
import com.kaishengit.crm.mapper.SalesMapper;
import com.kaishengit.crm.service.RecordSalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RecordSalesRecordServiceImpl implements RecordSalesRecordService {

    @Autowired
    private RecordSalesRecordMapper recordSalesRecordMapper;

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<RecordSalesRecord> findBySalesRecordId(Integer id) {
        return recordSalesRecordMapper.findBySalesRecordId(id);
    }

    /**
     * 添加新纪录
     * @param recordSalesRecord
     */
    @Override
    @Transactional
    public void saveRecord(RecordSalesRecord recordSalesRecord) {
        //添加跟进记录
        recordSalesRecord.setCreateTime(new Date());
        recordSalesRecordMapper.saveRecord(recordSalesRecord);
        //修改销售机会跟进时间
        SalesRecord salesRecord = salesMapper.findById(recordSalesRecord.getsalersrecordId());
        salesRecord.setFollowTime(new Date());
        salesMapper.updateSales(salesRecord);
        //修改客户的最后跟进时间
        Customer customer = customerMapper.findCustomerById(salesRecord.getCustomerId());
        customer.setLastContactTime(new Date());
        customerMapper.updateCustomer(customer);
    }
}
