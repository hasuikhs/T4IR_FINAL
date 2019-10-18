package com.chavis.biz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.chavis.biz.dao.CarDAO_MyBatis;
import com.chavis.biz.dao.MemberDAO;
import com.chavis.biz.vo.MemberVO;
import com.chavis.biz.vo.NotificationVO;
import com.chavis.biz.vo.ReservationVO;

@Service("memberservice")
public class MemberServiceImpl implements MemberService {

	public static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Resource(name = "membermybatis")
	MemberDAO dao;

	@Autowired
	ApplicationContext context;

	public MemberServiceImpl() {
		log.info("MemberService 시작");
	}

	public int dupcheck(String member_id) {
		String msg = "";
		int flag = 0;
		MemberVO vo = null;
		try {
			vo = dao.dupcheck(member_id);
			if (vo != null) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(msg);
		return flag;
	}

	public MemberVO login(String member_id, String password) {
		String msg = "";
		MemberVO vo = null;
		try {
			vo = dao.login(member_id, password);
			Map<String, String> log = new HashMap<String, String>();
			if (vo != null) {
				msg = context.getMessage("login.success", new Object[] { vo.getMember_mname() }, Locale.KOREA);

				log.put("code", "200");
				log.put("message", "login success");
			} else {
				msg = context.getMessage("login.fail", new Object[] { member_id }, Locale.KOREA);
				log.put("code", "300");
				log.put("message", "login fail");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(msg);
		return vo;
	}

	public int addMember(MemberVO member) {
		return dao.addMember(member);
	}

	public int updateMember(Map<String, String> member) {
		System.out.println(member);
		return dao.updateMember(member);
	}

	public List<NotificationVO> getNotificationList(String member_id) {
		return dao.getNotificationList(member_id);
	}

	@Override
	public List<ReservationVO> getMemberReserveList(String id) {
		return dao.getMemberReserveList(id);
	}

	@Override
	public int updateCar(Map<String, String> map2) {
		return dao.updateCar(map2);
	}

	@Override
	public String findMemberID(Map<String, String> map) {
		return dao.findMemberID(map);
	}

	@Override
	public String findMemberPW(Map<String, String> map) {
		return dao.findMemberPW(map);
	}

}
