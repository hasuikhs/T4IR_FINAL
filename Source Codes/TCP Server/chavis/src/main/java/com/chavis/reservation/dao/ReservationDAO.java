package com.chavis.reservation.dao;

import java.util.List;

import com.chavis.reservation.vo.ReservationVO;

public interface ReservationDAO {
	// Create
	int addReservation(ReservationVO reservation);
	// Read
	ReservationVO getReservation(int reservation_no);
	// List
	List<ReservationVO> getReservationToday();
	// ListByID
	List<ReservationVO> getReservationByID(String member_id);
	// Update
	int updateReservation(ReservationVO reservation);
	// Delete
	int removeReservation(int reserve_no);
}
