package com.chavis.car.dao;

import java.util.List;

import com.chavis.car.vo.CarVO;

public interface CarDAO {
    // CREATE
    public int registerCar(CarVO car);

    // READ
    public CarVO getCar(int car_no);

    public List<CarVO> getCarList();

    // UPDATE
    public int resetTireDistance(int car_no);

    public int resetWiperDistance(int car_no);

    public int resetCooler(int car_no);

    public int resetEngineOil(int car_no);

    public int updateTireDistance(String distance, int car_no);

    public int updateWiperDistance(String distance, int car_no);

    public int updateCooler(String cooler_left, int car_no);

    public int updateEngineOil(String engineOil, int car_no);

    public int updateDistance(String distance, int car_no);

    // DELETE
    public int deleteCar(int car_no);
    
    // GET CAR_NO
    public int getCarNo(String car_id);
}