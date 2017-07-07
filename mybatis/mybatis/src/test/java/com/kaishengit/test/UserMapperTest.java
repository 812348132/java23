package com.kaishengit.test;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;

public class UserMapperTest {
	
	
	private Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
	private SqlSession sqlSession;
	private UserMapper userMapper;
	

	@Before
	public void before(){
		sqlSession = MyBatisUtil.getSession();
		//!!! MyBatis根据定义的Mapper接口动态的生成该接口的实现类
		//接口指向实现类
		//动态代理模式
		userMapper = sqlSession.getMapper(UserMapper.class);
	}
	@After
	public void after(){
		sqlSession.close();
	}
	
	/*@Test
	public void findById(){
		
		User user = userMapper.findById(1);
		
		logger.debug("user: {}",user);
	}*/
	
	@Test
	public void findById(){
		
		SqlSession session1 = MyBatisUtil.getSession();
		UserMapper userMapper1 = session1.getMapper(UserMapper.class);
		
		User user1 = userMapper1.findById(1);
		
		session1.close();
		System.out.println(user1.getUserName());
		
		
		SqlSession session2 = MyBatisUtil.getSession();
		UserMapper userMapper2 = session2.getMapper(UserMapper.class);
		
		User user2 = userMapper2.findById(1);
		
		session2.close();
		System.out.println(user2.getUserName());
	}
	
	
	@Test
	public void findAll(){
		
		List<User> userList = userMapper.findAll();
		
		for(User user : userList) {
			logger.debug("userName:{}",user.getUserName());
		}
	}
	
	@Test
	public void findAllLoadDept(){
		
		List<User> userList = userMapper.findAllLoadDept();
		
		for(User user : userList) {
			logger.debug("userName:{} ->  deptName:{}",user.getUserName(),user.getDept().getDeptName());
		}
	}

	@Test
	public void findByIdAndPassword(){
		    User user = userMapper.findByIdAndPassword("tom","上海");
		    System.out.print(user.getId());
	}

	@Test
	public void findByMapParam(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name","tom");
		map.put("address","上海");

		User user = userMapper.findByMapParam(map);
		System.out.print(user.getAddress());
	}

	@Test
	public void searchByNameAndAddress(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name","tom");
		map.put("address","北京");

		List<User> userList = userMapper.searchByNameAndAddress(map);

		for (User user: userList) {
			System.out.println(user.getId());
		}
	}

	@Test
	public void batchSave(){
		List<User> userList = Arrays.asList(new User("Jack","美国","111111",1),
				new User("海森堡","德国","111111",1),
				new User("李四","中国","111111",1));

		userMapper.batchSave(userList);
		sqlSession.commit();
	}

	@Test
	public void save(){
		User user = new User("李四","中国","111111",1);
		userMapper.save(user);

		sqlSession.commit();

		System.out.println(user.getId());

	}

	@Test
	public void delByIds(){
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(5);
		idList.add(6);

		userMapper.delByIds(idList);
		sqlSession.commit();
	}
}
