package com.chavis.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chavis.biz.vo.BodyshopVO;
import com.chavis.biz.vo.ReservationListVO;

@Component("bodyshopmybatis")
public class BodyshopDAO_MyBatis implements BodyshopDAO {
	
	public static Logger log = LoggerFactory.getLogger(BodyshopDAO_MyBatis.class);
	
	@Autowired
	SqlSession sqlSession;

	public BodyshopDAO_MyBatis() {
		log.info("BodyshopDAO 시작");
	}

	@Override
	public BodyshopVO bodyshopLogin(String bodyshop_id, String bodyshop_pw) {
		BodyshopVO bodyshop = new BodyshopVO();
		bodyshop.setBodyshop_id(bodyshop_id);
		bodyshop.setBodyshop_pw(bodyshop_pw);
		return sqlSession.selectOne("bodyshopMapper.bodyshopLogin", bodyshop);
	}

	@Override
	public void addBodyshop(BodyshopVO bodyshop) {
		sqlSession.insert("bodyshopMapper.addBodyshop", bodyshop);
	}

	@Override
	public List<BodyshopVO> getBodyshoplist() {
		return sqlSession.selectList("bodyshopMapper.getBodyshoplist");
	}

	@Override
	public List<ReservationListVO> getReservationList(int id) {
		return sqlSession.selectList("reservationMapper.getReservationList", id);
	}

	@Override
	public int getNo() {
		return sqlSession.selectOne("bodyshopMapper.getNo");
	}

	@Override
	public String findBodyshopID(Map<String, String> map) {
		return sqlSession.selectOne("bodyshopMapper.findBodyshopID", map.get("bodyshop_name"));
	}

	@Override
	public String findBodyshopPW(Map<String, String> map) {
		return sqlSession.selectOne("bodyshopMapper.findBodyshopPW", map);
	}

}
