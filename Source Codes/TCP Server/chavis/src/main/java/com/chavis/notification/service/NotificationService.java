package com.chavis.notification.service;

import com.chavis.notification.vo.NotificationVO;

public interface NotificationService {
	int addNotification(NotificationVO vo);
	int removeNotification(int member_no);
}
