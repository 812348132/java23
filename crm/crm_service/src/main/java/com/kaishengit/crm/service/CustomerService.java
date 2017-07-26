package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.io.OutputStream;
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

    void updateCustomerToPublic(Customer customer,Account account);

    void transferToAccount(Customer customer, Account newAccount,Account account);

    void customerToExcel(Account account, OutputStream outputStream);

    void transferMy(Customer customer, Account account);

    void savePublicCustomer(Customer customer);

    void customerPublicToExcel(OutputStream outputStream);
}

