package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;

public interface UserRegionRoleService {

	List<UserRegionRole> findAll();
	
	UserRegionRole findById(Integer id);
	
	UserRegionRole save(UserRegionRole userRegionRole);
	
	Page<UserRegionRole> findAllByPage(Pageable pageable);
	
	List<UserRegionRole> findAssignedRegionsByUserId(Integer userid);
	List<UserRegionRole> findAssignedRegionsByUserName(String username);
	List<Project> findAssignedProjectsByUserName(String username);
}
