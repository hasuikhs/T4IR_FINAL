package com.chavis.biz.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chavis.biz.dao.ReservationDAO;
import com.chavis.biz.vo.ReservationVO;
import com.chavis.biz.vo.WebTableVO;

@Service("reservationservice")
public class ReservationServiceImpl implements ReservationService {

	private static Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Resource(name = "reservationmybatis")
	ReservationDAO dao;

	@Autowired
	ApplicationContext context;

	public ReservationServiceImpl() {
		log.info("ReservationService 시작");
	}

	public ReservationServiceImpl(ReservationDAO dao) {
		super();
		this.dao = dao;
	}

	@Transactional(rollbackFor = Exception.class) 
	@Override
	public int addReservation(Map<String, String> reservation) throws SQLException{
		return dao.addReservation(reservation);
	}

    @Override
	public List<ReservationVO> getReservationByBodyshopNo(int bodyshop_no) {
		return dao.getReservationByBodyshopNo(bodyshop_no);
	}

	@Override
	public List<ReservationVO> getReservationByID(String member_id) {
		return dao.getReservationByID(member_id);
	}

	@Override
	public List<WebTableVO> getReservationForWeb(int bodyshop_no) {
		return dao.getReservationForWeb(bodyshop_no);
	}

	
}
