package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by zjs on 2017/7/20.
 */
public interface CustomerService {

    List<String> findAllTrade();

    List<String> findAllSource();

    void saveCustomer(Customer customer, Account account);

    PageInfo<Customer> findMyCustomerByParam(Map<String, Object> params);

    Customer findCustomerById(Integer id);

    void updateCustomer(Customer customer);

    void delCustomerById(Integer id);
}

