package com.chavis.biz.service;

import javax.annotation.Resource;

import com.chavis.biz.dao.CarDAO;
import com.chavis.biz.dao.CarDAO_MyBatis;
import com.chavis.biz.vo.CarVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("carservice")
public class CarServiceImpl implements CarService {

	public static Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
	
	@Resource(name = "carmybatis")
	CarDAO dao;

	@Autowired
	ApplicationContext context;

	public CarServiceImpl() {
		log.info("CarService 시작");
	}

	public CarServiceImpl(CarDAO dao) {
		this.dao = dao;
	}

	@Override
	public CarVO getPersonalCar(String member_id) {
		return dao.getPersonalCar(member_id);
	}

}
