package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SalesRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zjs on 2017/7/20.
 */
public interface CustomerMapper {
    void saveCustomer(Customer customer);

    List<Customer> findMyCustomerByParams(Map<String, Object> params);

    Customer findCustomerById(Integer id);

    void updateCustomer(Customer customer);

    void delCustomerById(Integer id);

    List<Customer> findCustomerByAccountId(@Param("accountId") Integer id);

    List<Customer> findAllCustomer();
}
