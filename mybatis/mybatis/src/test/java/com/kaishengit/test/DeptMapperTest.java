package com.kaishengit.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaishengit.entity.Dept;
import com.kaishengit.entity.User;
import com.kaishengit.mapper.DeptMapper;
import com.kaishengit.util.MyBatisUtil;

public class DeptMapperTest {

	Logger logger = LoggerFactory.getLogger(DeptMapperTest.class);

	@Test
	public void findByIdLoadUser(){
		
		SqlSession sqlSession = MyBatisUtil.getSession();
		
		DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
		
		Dept dept = deptMapper.findByIdLoadUser(1);
		System.out.println(dept.getDeptName());
		
		List<User> userList = dept.getUserList();
		
		for(User user : userList) {
			System.out.println(user.getUserName());
		}
		
		sqlSession.close();
		
	}
	
}
