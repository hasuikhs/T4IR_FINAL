package com.chavis.car.service;

import java.util.List;

import com.chavis.car.vo.CarVO;

public interface CarService {
	// CREATE (차 등록)
	int registerCar(CarVO vo);

	// READ (단일 차량 정보 조회, 전체 차량 정보 조회)
	CarVO getCar(int car_no);

	List<CarVO> getCarList();

	// UPDATE(각종 소모품 기록 초기화 및 수정)
	int resetTireDistance(int car_no);

	int resetWiperDistance(int car_no);

	int resetCooler(int car_no);

	int resetEngineOil(int car_no);

	int updateTireDistance(String distance, int car_no);

	int updateWiperDistance(String distance, int car_no);

	int updateCooler(String cooler_left, int car_no);

	int updateEngineOil(String engineOil, int car_no);

	int updateDistance(String distance, int car_no);

	// DELETE(차량 정보 삭제)
	int deleteCar(int car_no);
	
	// GET CAR_NO
    public int getCarNo(String car_id);
}
