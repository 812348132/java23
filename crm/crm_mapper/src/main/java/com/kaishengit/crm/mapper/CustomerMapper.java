package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by zjs on 2017/7/20.
 */
public interface CustomerMapper {
    void saveCustomer(Customer customer);

    List<Customer> findMyCustomerByParams(Map<String, Object> params);
}
