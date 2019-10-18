package com.chavis.biz.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.chavis.biz.vo.ReservationVO;
import com.chavis.biz.vo.WebTableVO;

public interface ReservationService {
	// Create
	int addReservation(Map<String, String> map) throws SQLException;

	// ListByID
	List<ReservationVO> getReservationByID(String member_id);

    // ListByBodyshopNo
    List<ReservationVO> getReservationByBodyshopNo(int bodyshop_no);
    // ListForWebTable
    List<WebTableVO> getReservationForWeb(int bodyshop_no);
}
