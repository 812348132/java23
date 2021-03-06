package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.AccountDeptExample;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.DeptExample;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.crm.weixin.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zjs on 2017/7/17.
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private AccountDeptMapper accountDeptMapper;

    @Autowired
    private WeiXinUtil weixinUtil;

    @Override
    public List<Dept> findAll() {
        return deptMapper.selectByExample(new DeptExample());
    }

    /**
     * 添加部门
     * @param dept
     */
    @Override
    @Transactional
    public void save(Dept dept) {
        deptMapper.insert(dept);
        //同步到微信
        weixinUtil.createDept(dept.getId(),dept.getpId(),dept.getDeptName());
    }

    /**
     * 删除部门
     * @param id
     */
    @Override
    @Transactional
    public void delDeptById(Integer id) {
        //删除关系表
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andDeptIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);
        //删除部门
        DeptExample deptExample = new DeptExample();
        deptExample.createCriteria().andIdEqualTo(id);
        deptMapper.deleteByExample(deptExample);

        //同步到微信
        weixinUtil.deleteDeptById(id.toString());
    }
}
