package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountDeptExample;
import com.kaishengit.crm.entity.AccountDeptKey;
import com.kaishengit.crm.entity.AccountExample;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.exception.AuthenticationException;
import com.kaishengit.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zjs on 2017/7/17.
 */
@Service
public class AccountServiceImpl implements AccountService{

    @Value("${password.salt}")
    private String passwordSalt;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountDeptMapper accountDeptMapper;
    @Override
    public List<Account> findAllAccount() {
        return accountMapper.selectByExample(new AccountExample());
    }

    /**
     * 添加员工
     * @param account
     * @param deptIds
     */
    @Override
    @Transactional
    public void saveAccount(Account account, Integer[] deptIds) {
        account.setPassword(DigestUtils.md5Hex(passwordSalt+account.getPassword()));
        account.setCreateTime(new Date());
        accountMapper.insert(account);
        for (Integer deptId : deptIds) {
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setAccountId(account.getId());
            accountDeptKey.setDeptId(deptId);

            accountDeptMapper.insert(accountDeptKey);
        }
    }

    @Override
    public Long countAll() {
        return accountMapper.countByExample(new AccountExample());
    }

    @Override
    public Long countByDeptId(Integer id) {
        if(new Integer(1000).equals(id)){
            id = null;
        }
        return accountMapper.countByDeptId(id);
    }

    @Override
    public List<Account> findByDeptId(Integer id) {
        if(new Integer(1000).equals(id)) {
            id = null;
        }
        return accountMapper.findByDeptId(id);
    }

    /**
     * 删除员工
     * @param id
     */
    @Override
    @Transactional
    public void delAccountById(Integer id) {
        //删除关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);
        //删除员工
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(id);
        accountMapper.deleteByExample(accountExample);
    }

    /**
     * 登录
     * @param
     * @param password
     */
    @Override
    public Account login(String mobile, String password) {
        //查询是否存在
        Account account = accountMapper.findByMobileLoadDept(mobile);
        if(account == null) {
            throw new AuthenticationException("账号或密码错误");
        }
        //判断password是否相同
        if(account.getPassword().equals(DigestUtils.md5Hex(passwordSalt + password))) {
            return account;
        }
        throw new AuthenticationException("账号或密码错误");
    }

    /**
     * 修改员工密码
     * @param oldPassword  旧密码
     * @param password     新密码
     * @param account      当前员工对象
     * @throws ServiceException   抛出异常
     */
    @Override
    public void changePassword(String oldPassword, String password, Account account) throws ServiceException{
        oldPassword = DigestUtils.md5Hex(passwordSalt + oldPassword);
        if(account.getPassword().equals(oldPassword)) {
            account.setPassword(DigestUtils.md5Hex(passwordSalt + password));
            accountMapper.updateByPrimaryKeySelective(account);
        } else {
            throw  new ServiceException("旧密码不正确");
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(accountId);
        Account account = accountMapper.selectByExample(accountExample).get(0);
        return account;
    }
}
