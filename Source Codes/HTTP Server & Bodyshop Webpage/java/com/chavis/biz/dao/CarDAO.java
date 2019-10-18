package com.chavis.biz.dao;

import com.chavis.biz.vo.CarVO;

public interface CarDAO {

	public CarVO getPersonalCar(String member_id);

}