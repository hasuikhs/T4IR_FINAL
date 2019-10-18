package com.chavis.notification.service;

import com.chavis.notification.dao.NotificationDAO;
import com.chavis.notification.vo.NotificationVO;

public class NotificationServiceImpl implements NotificationService{
	NotificationDAO dao;
	public NotificationServiceImpl(NotificationDAO dao) {
		this.dao = dao;
	}	
	
	@Override
	public int addNotification(NotificationVO vo) {
		return dao.addNotification(vo);
	}

	@Override
	public int removeNotification(int member_no) {
		return dao.removeNotification(member_no);
	}

}
