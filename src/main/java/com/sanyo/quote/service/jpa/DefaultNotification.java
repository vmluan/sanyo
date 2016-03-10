package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Notification;
import com.sanyo.quote.repository.NotificationRepository;

@Service("notificationService")
@Repository
@Transactional
public class DefaultNotification {
	@Autowired
	private NotificationRepository notificationRepository;

	@Transactional(readOnly=true)
	public List<Notification> findAll() {
		return Lists.newArrayList(notificationRepository.findAll());
	}

	@Transactional(readOnly=true)
	public Notification findById(Integer id) {
		return notificationRepository.findOne(id);
	}

	public Notification save(Notification notification) {
		return notificationRepository.save(notification);
	}


}
