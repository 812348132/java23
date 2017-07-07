package com.kaishengit.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Aaa;
import com.kaishengit.entity.AaaExample;
import com.kaishengit.mapper.AaaMapper;
import com.kaishengit.util.MyBatisUtil;

public class AaaMapperTest {

	private SqlSession sqlSession;
	private AaaMapper aaaMapper;
	
	@Before
	public void before() {
		sqlSession = MyBatisUtil.getSession();
		aaaMapper = sqlSession.getMapper(AaaMapper.class);
	}
	@After
	public void after() {
		sqlSession.close();
	}
	
	
	
}
