package com.chavis.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chavis.biz.vo.MemberVO;
import com.chavis.biz.vo.NotificationVO;
import com.chavis.biz.vo.ReservationVO;

@Component("membermybatis")
public class MemberDAO_MyBatis implements MemberDAO {

	public static Logger log = LoggerFactory.getLogger(MemberDAO_MyBatis.class);
	
	@Autowired
	SqlSession sqlSession;

	public MemberDAO_MyBatis() {
		log.info("MemberDAO 시작");
	}

	public MemberVO dupcheck(String Member_id) {
		MemberVO membervo = new MemberVO();
		membervo.setMember_id(Member_id);

		return sqlSession.selectOne("memberMapper.dupcheck", membervo);
	}

	public MemberVO login(String Member_id, String member_pw) {
		MemberVO membervo = new MemberVO();
		membervo.setMember_id(Member_id);
		membervo.setMember_pw(member_pw);

		return sqlSession.selectOne("memberMapper.memberLogin", membervo);
	}

	public int addMember(MemberVO member) {
		return sqlSession.insert("memberMapper.addMember", member);
	}

	public int updateCar(Map<String, String> member) {
		return sqlSession.update("memberMapper.updateCar", member);
	}

	public List<NotificationVO> getNotificationList(String member_id) {
		return sqlSession.selectList("memberMapper.getNotificationList", member_id);
	}

	public List<ReservationVO> getMemberReserveList(String id) {
		return sqlSession.selectList("memberMapper.getMemberReserveList", id);
	}

	public int updateMember(Map<String, String> member) {
		return sqlSession.update("memberMapper.updateMember", member);
	}

	@Override
	public String findMemberID(Map<String, String> map) {
		return sqlSession.selectOne("memberMapper.findMemberID", map);
	}

	@Override
	public String findMemberPW(Map<String, String> map) {
		return sqlSession.selectOne("memberMapper.findMemberPW", map);
	}
}
