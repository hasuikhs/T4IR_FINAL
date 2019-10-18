package com.chavis.biz.dao;

import java.util.List;
import java.util.Map;

import com.chavis.biz.vo.BodyshopVO;
import com.chavis.biz.vo.ReservationListVO;

public interface BodyshopDAO {
	BodyshopVO bodyshopLogin(String bodyshop_id, String bodyshop_pw);

	int getNo();

	void addBodyshop(BodyshopVO vo);

	List<BodyshopVO> getBodyshoplist();

	List<ReservationListVO> getReservationList(int member_no);
	
	String findBodyshopID(Map<String, String> bodyshop_name);
	
	String findBodyshopPW(Map<String, String> map);
	
}
