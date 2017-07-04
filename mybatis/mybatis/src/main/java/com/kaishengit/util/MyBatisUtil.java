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
			//1.��ȡ�����ļ�
			Reader reader = Resources.getResourceAsReader("mybatis.xml");
			//2.����SqlSessionFactoryBuilder ����������
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			//3.��������
			return sqlSessionFactoryBuilder.build(reader);
			
		} catch (IOException e) {
			throw new RuntimeException("��ȡSqlSessionFactory�쳣",e);
		}
	}
	
	
	public static SqlSessionFactory buldeSqlSessionFactory(){
		return sqlSessionFactory;
	}
	
	public static SqlSession getSession(){
		return buldeSqlSessionFactory().openSession();
	}
	
}
