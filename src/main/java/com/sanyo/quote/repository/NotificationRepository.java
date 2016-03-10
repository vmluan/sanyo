package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.Notification;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Integer> {
//	@Query("select c from Category c where c.categoryID  IN :ids")
//	List<Category> findByIds(@Param("ids") List<Integer> ids);
	@Query("select n from Notification n")
	List<Notification> findParents();

}
