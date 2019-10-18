package com.chavis.notification.dao;

import com.chavis.notification.vo.NotificationVO;

public interface NotificationDAO {
	int addNotification(NotificationVO vo);
	int removeNotification(int member_no);
}
