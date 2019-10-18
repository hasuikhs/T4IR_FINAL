package com.chavis.biz.dao;

import java.util.List;
import java.util.Map;

import com.chavis.biz.vo.MemberVO;
import com.chavis.biz.vo.NotificationVO;
import com.chavis.biz.vo.ReservationVO;

public interface MemberDAO {
	MemberVO login(String member_id, String member_pw);

	int addMember(MemberVO member);

	int updateMember(Map<String, String> member);

	int updateCar(Map<String, String> map2);

	MemberVO dupcheck(String member_id);

	List<NotificationVO> getNotificationList(String member_id);

	List<ReservationVO> getMemberReserveList(String id);
	
	String findMemberID(Map<String, String> map);

	String findMemberPW(Map<String, String> map);
}
