package com.kaishengit.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaishengit.entity.User;
import com.kaishengit.util.MyBatisUtil;

public class MyBatisTest {
	
	private Logger logger = LoggerFactory.getLogger(MyBatisTest.class);
	
	@Before
	public void before(){
		
	}

	@Test
	public void findById() throws Exception{
		
		
		SqlSession sqlSession = MyBatisUtil.getSession();
		User user = sqlSession.selectOne("com.kaishengit.mapper.UserMapper.findById",1);
		System.out.println(user.getUserName());
		System.out.println(user.getAddress());
		
		sqlSession.close();
	}
	
	
	@Test
	public void findAll(){
		SqlSession sqlSession = MyBatisUtil.getSession();
		
		List<User> userList = sqlSession.selectList("com.kaishengit.mapper.UserMapper.findAll");
		
		for(User user : userList) {
			logger.debug("{} -> {} -> {}", user.getId(),user.getUserName(),user.getAddress());
		}
		
		sqlSession.close();
	}
	
	
	
	
	
}
