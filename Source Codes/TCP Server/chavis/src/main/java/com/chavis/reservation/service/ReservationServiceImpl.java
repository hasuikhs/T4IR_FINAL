package com.chavis.reservation.service;

import java.util.List;



import com.chavis.reservation.dao.ReservationDAO;
import com.chavis.reservation.dao.ReservationDAO_mybatis;
import com.chavis.reservation.vo.ReservationVO;

public class ReservationServiceImpl implements ReservationService {

    ReservationDAO dao;


    public ReservationServiceImpl(ReservationDAO dao)   {
        this.dao = dao;
    }

    @Override
    public int addReservation(ReservationVO reservation) {
        return dao.addReservation(reservation);
    }

    @Override
    public ReservationVO getReservation(int reservation_no) {
        return dao.getReservation(reservation_no);
    }

    @Override
    public List<ReservationVO> getReservationToday() {
        return dao.getReservationToday();
    }

    @Override
    public int updateReservation(ReservationVO reservation) {
        return dao.updateReservation(reservation);
    }

    @Override
    public int removeReservation(int reserve_no) {
        return dao.removeReservation(reserve_no);
    }

	@Override
	public List<ReservationVO> getReservationByID(String member_id) {
		return dao.getReservationByID(member_id);
	}

}
