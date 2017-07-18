package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;

import java.util.List;

/**
 * Created by zjs on 2017/7/17.
 */
public interface AccountService {

    List<Account> findAllAccount();

    void saveAccount(Account account, Integer[] deptId);
}
