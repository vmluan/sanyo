package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Group;

public interface GroupService {

	List<Group> findAll();
	List<Group> findByUserName(String userName);
	Group findById(Integer id);
	
	Group save(Group group);
	
	Page<Group> findAllByPage(Pageable pageable);	
	
}
