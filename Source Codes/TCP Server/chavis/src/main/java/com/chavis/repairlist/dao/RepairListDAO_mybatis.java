package com.chavis.repairlist.dao;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.chavis.repairlist.vo.RepairListVO;

public class RepairListDAO_mybatis implements RepairListDAO{
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
	public RepairListDAO_mybatis() {
		sqlSession = sessionFactory.openSession();
	}
	
	
	@Override
	public int addRepairList(RepairListVO vo) {
		int result = sqlSession.insert("repairlistMapper.addRepairList", vo);
		sqlSession.commit();
		return result;
	}

}
