package com.chavis.biz.dao;

import java.util.List;
import java.util.Map;

import com.chavis.biz.vo.ReservationVO;
import com.chavis.biz.vo.WebTableVO;

public interface ReservationDAO {
	// Create
	int addReservation(Map<String, String> reservation);

	// ListByID
	List<ReservationVO> getReservationByID(String member_id);

	// ListByBodyshopNo
	List<ReservationVO> getReservationByBodyshopNo(int bodyshop_no);

	// ListReservationForWeb
	List<WebTableVO> getReservationForWeb(int bodyshop_no);

	ReservationVO getRecentReservation();
}
