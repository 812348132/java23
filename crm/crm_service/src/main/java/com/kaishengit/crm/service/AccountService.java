package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;

import java.util.List;

/**
 * Created by zjs on 2017/7/17.
 */
public interface AccountService {

    List<Account> findAllAccount();

    void saveAccount(Account account, Integer[] deptId);

    Long countAll();

    Long countByDeptId(Integer id);

    List<Account> findByDeptId(Integer id);

    void delAccountById(Integer id);

    Account login(String mobile, String password);

    void changePassword(String oldPassword, String password, Account account);
}
