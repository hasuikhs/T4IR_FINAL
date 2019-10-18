package com.chavis.notification.dao;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.chavis.notification.vo.NotificationVO;

public class NotificationDAO_mybatis implements NotificationDAO{
	private static SqlSessionFactory sessionFactory = null;
	private SqlSession sqlSession;
	static {
		try {
			if(sessionFactory == null) {
				Reader reader = Resources.getResourceAsReader("mybatisXml/mybatis_config.xml");
				sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public NotificationDAO_mybatis() {
		sqlSession = sessionFactory.openSession();
	}
	@Override
	public int addNotification(NotificationVO vo) {
		System.out.println("NotificationDAO_mybatis_addNotification");
		int result = sqlSession.insert("notificationMapper.addNotification",vo);
		sqlSession.commit();
		return result;
	}

	@Override
	public int removeNotification(int member_no) {
		System.out.println("NotificationDAO_mybatis_removeNotification");
		int result = sqlSession.delete("notificationMapper.removeNotification", member_no);
		sqlSession.commit();
		return result;
	}

}
