package com.sanyo.quote.service;

import java.util.List;



import com.sanyo.quote.domain.Notification;

public interface NotificationService {
List<Notification> findAll();
	
Notification findById(Integer id);
	
Notification save(Notification notification);

}