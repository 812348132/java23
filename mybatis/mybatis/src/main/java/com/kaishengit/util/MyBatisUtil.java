package com.kaishengit.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

	private static SqlSessionFactory sqlSessionFactory = getSalSessionFactory();

	private static SqlSessionFactory getSalSessionFactory() {
		
		try {
			//1.读取配置文件
			Reader reader = Resources.getResourceAsReader("mybatis.xml");
			//2.创建SqlSessionFactoryBuilder 工厂创建者
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			//3.创建工厂
			return sqlSessionFactoryBuilder.build(reader);
			
		} catch (IOException e) {
			throw new RuntimeException("获取SqlSessionFactory异常",e);
		}
	}
	
	
	public static SqlSessionFactory buldeSqlSessionFactory(){
		return sqlSessionFactory;
	}
	
	public static SqlSession getSession(){
		return buldeSqlSessionFactory().openSession();
	}
	
}
