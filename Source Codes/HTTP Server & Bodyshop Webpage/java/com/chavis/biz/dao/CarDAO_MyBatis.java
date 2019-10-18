package com.chavis.biz.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chavis.biz.vo.CarVO;

@Component("carmybatis")
public class CarDAO_MyBatis implements CarDAO {

	public static Logger log = LoggerFactory.getLogger(CarDAO_MyBatis.class);
	
	@Autowired
	SqlSession sqlSession;

	public CarDAO_MyBatis() {
		log.info("CarDAO 시작");
	}

	@Override
	public CarVO getPersonalCar(String member_id) {
		return sqlSession.selectOne("carMapper.getPersonalCar", member_id);
	}
}
