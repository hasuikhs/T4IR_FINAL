package com.chavis.car.dao;

import java.io.Reader;
import java.util.List;

import com.chavis.car.vo.CarVO;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CarDAO_mybatis implements CarDAO {

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
	public CarDAO_mybatis() {
		sqlSession = sessionFactory.openSession();
	}
	// CREATE
	@Override
	public int registerCar(CarVO car) {
		int result = sqlSession.insert("carMapper.addCar", car);
		sqlSession.commit();
		return result;
	}

	// READ
	@Override
	public CarVO getCar(int car_no) {
		return sqlSession.selectOne("carMapper.getCar", car_no);
	}

	@Override
	public List<CarVO> getCarList() {
		return sqlSession.selectList("carMapper.getCarList");
	}

	// UPDATE
	@Override
	public int resetTireDistance(int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setTire_change_distance("0");
		int result = sqlSession.update("carMapper.updateTireDistance", car);
		sqlSession.commit();
		return result;

	}

	@Override
	public int resetWiperDistance(int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setWiper_change_distance("0");
		int result = sqlSession.update("carMapper.updateWiperDistance", car);
		sqlSession.commit();
		return result;

	}

	@Override
	public int resetCooler(int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setCooler_left("100");
		int result = sqlSession.update("carMapper.updateCooler", car);
		sqlSession.commit();
		return result;
	}



	@Override
	public int resetEngineOil(int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setEngine_oil_viscosity("100");
		int result = sqlSession.update("carMapper.updateEngineOil", car);
		sqlSession.commit();
		return result;

	}

	@Override
	public int updateTireDistance(String distance, int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setTire_change_distance(distance);
		int result = sqlSession.update("carMapper.updateTireDistance", car);
		sqlSession.commit();
		return result;
	}

	@Override
	public int updateWiperDistance(String distance, int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setWiper_change_distance(distance);
		int result = sqlSession.update("carMapper.updateWiperDistance", car);
		sqlSession.commit();
		return result;

	}

	@Override
	public int updateCooler(String cooler_left, int car_no) {
		CarVO car = new CarVO();
		car.setCooler_left(cooler_left);
		car.setCar_no(car_no);
		int result = sqlSession.update("carMapper.updateCooler", car);
		sqlSession.commit();
		return result;

	}

	@Override
	public int updateEngineOil(String engineOil, int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setEngine_oil_viscosity(engineOil);
		int result = sqlSession.update("carMapper.updateEngineOil", car);
		sqlSession.commit();
		return result;
	}

	@Override
	public int updateDistance(String distance, int car_no) {
		CarVO car = new CarVO();
		car.setCar_no(car_no);
		car.setDistance(distance);
		int result = sqlSession.update("carMapper.updateDistance", car);
		sqlSession.commit();
		return result;

	}

	@Override
	public int deleteCar(int car_no) {
		int result = sqlSession.delete("carMapper.removeCar", car_no);
		sqlSession.commit();
		return result;
	}
	
	@Override
	public int getCarNo(String car_id) {
		return sqlSession.selectOne("carMapper.getCarNo", car_id);
	}
}
