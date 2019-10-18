package com.chavis.repairlist.service;

import com.chavis.repairlist.dao.RepairListDAO;
import com.chavis.repairlist.vo.RepairListVO;

public class RepairListServiceImpl implements RepairListService{
	RepairListDAO dao;
	
	public RepairListServiceImpl(RepairListDAO dao) {
		this.dao = dao;
	}
	
	
	@Override
	public int addRepairList(RepairListVO vo) {
		// TODO Auto-generated method stub
		return dao.addRepairList(vo);
	}

}
