package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private CustomerMapper customerMapper;

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
    }

    @Override
    public PageInfo<Customer> findMyCustomerByParam(Map<String, Object> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        PageHelper.startPage(pageNum,10);
        List<Customer> customers = customerMapper.findMyCustomerByParams(params);
        return new PageInfo<>(customers);
    }
}
