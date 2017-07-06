package com.kaishengit.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

	private static SqlSessionFactory sqlSessionFactory = getSalSessionFactory();


	private static SqlSessionFactory getSalSessionFactory() {
		
		try {
			Reader reader = Resources.getResourceAsReader("mybatis.xml");

			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);

			return sqlSessionFactory;

		} catch (IOException e) {
			throw new RuntimeException("ªÒ»°SqlSessionFactory“Ï≥£",e);
		}
	}
	
	
	public static SqlSessionFactory buldeSqlSessionFactory(){
		return sqlSessionFactory;
	}


	public static SqlSession getSession(){
		return buldeSqlSessionFactory().openSession();
	}
	
}
