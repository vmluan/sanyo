package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.UserRegionRole;

public interface UserRegionRoleService {

	List<UserRegionRole> findAll();
	
	UserRegionRole findById(Integer id);
	
	UserRegionRole save(UserRegionRole userRegionRole);
	
	Page<UserRegionRole> findAllByPage(Pageable pageable);	
	
}
