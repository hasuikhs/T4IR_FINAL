package com.chavis.reservation.dao;

import java.io.Reader;
import java.util.List;

import com.chavis.reservation.vo.ReservationVO;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class ReservationDAO_mybatis implements ReservationDAO {

	private static SqlSessionFactory sessionFactory = null;
	private SqlSession sqlSession;
	static {
		try {
			if(sessionFactory == null) {
				Reader reader = Resources.getResourceAsReader("mybatisXml/mybatis_config.xml");
				sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ReservationDAO_mybatis() {
		sqlSession = sessionFactory.openSession();
	}
	
	@Override
    public int addReservation(ReservationVO reservation) {
		int result = sqlSession.insert("reservationMapper.addReservation",reservation);
		sqlSession.commit();
		return result;
		
    }
	
    @Override
    public ReservationVO getReservation(int reservation_no) {

        return sqlSession.selectOne("reservationMapper.getReservation", reservation_no);
    }

    @Override
    public List<ReservationVO> getReservationToday() {

        return sqlSession.selectList("reservationMapper.listReservation");

    }

    
    public List<ReservationVO> getReservationWeek() {
    	return sqlSession.selectList("reserveMapper.listReservation");
	}


    @Override
    public int updateReservation(ReservationVO reservation) {
        int result = sqlSession.update("reservationMapper.updateReservation", reservation);
        sqlSession.commit();
        return result;
    }

    @Override

    public int removeReservation(int reservation_no) {
        int result = sqlSession.delete("reservationMapper.removeReservation", reservation_no);
        sqlSession.commit();
        return result;
    }

	@Override
	public List<ReservationVO> getReservationByID(String member_id) {
		return sqlSession.selectList("reservationMapper.listReservationByID", member_id);
	}

}
