package com.chavis.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chavis.biz.vo.ReservationVO;
import com.chavis.biz.vo.WebTableVO;

@Component("reservationmybatis")
public class ReservationDAO_MyBatis implements ReservationDAO {

	public static Logger log = LoggerFactory.getLogger(ReservationDAO_MyBatis.class);
	
	@Autowired
	SqlSession sqlSession;

	public ReservationDAO_MyBatis() {
		log.info("ReservationDAO 시작");
	}

	@Override
	public int addReservation(Map<String, String> reservation) {
		return sqlSession.insert("reservationMapper.addReservation", reservation);
	}

	@Override
	public List<ReservationVO> getReservationByBodyshopNo(int bodyshop_no) {
		return sqlSession.selectList("reservationMapper.getReservationByBodyshopNo", bodyshop_no);
	}

	@Override
	public List<WebTableVO> getReservationForWeb(int bodyshop_no) {
		return sqlSession.selectList("reservationMapper.getReservationForWeb", bodyshop_no);
	}

	@Override
	public List<ReservationVO> getReservationByID(String member_id) {
		return sqlSession.selectList("reservationMapper.listReservationByID", member_id);
	}

	@Override
	public ReservationVO getRecentReservation() {
		return sqlSession.selectOne("reservationMapper.getRecentReservation");
	}

}
