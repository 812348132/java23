package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SalesRecord;

import java.util.List;
import java.util.Map;

public interface SalesService {
    List<String> findCurrent();

    List<Customer> findAllCustomer();

    void saveSales(SalesRecord salesRecord,Account account,String content);

    PageInfo<SalesRecord> findAllSalesRecord(Map<String, Object> params);

    SalesRecord findSalesRecordById(Integer id);

    void updateSales(Integer id, String current);

    void delSalesRecordById(Integer id);

    List<SalesRecord> findAllSalesRecordByCustomerId(Integer id);
}
