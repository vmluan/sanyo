
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.repository.EncounterRepository;
import com.sanyo.quote.repository.UserRegionRoleRepository;
import com.sanyo.quote.service.UserRegionRoleService;

@Service("userRegionRoleService")
@Repository
@Transactional
public class DefaultUserRegionRoleService implements UserRegionRoleService {

	@Autowired
	private UserRegionRoleRepository userRegionRoleRepository;

	@Override
	public List<UserRegionRole> findAll() {
		return Lists.newArrayList(userRegionRoleRepository.findAll());
	}

	@Override
	public UserRegionRole findById(Integer id) {
		return userRegionRoleRepository.findOne(id);
	}

	@Override
	public UserRegionRole save(UserRegionRole userRegionRole) {
		return userRegionRoleRepository.save(userRegionRole);
	}

	@Override
	public Page<UserRegionRole> findAllByPage(Pageable pageable) {
		return userRegionRoleRepository.findAll(pageable);
	}

	@Override
	public List<UserRegionRole> findAssignedRegionsByUserId(Integer userid) {
		return userRegionRoleRepository.findAssignedRegionsByUserId(userid);
	}

	@Override
	public List<UserRegionRole> findAssignedRegionsByUserName(String username) {
		return userRegionRoleRepository.findAssignedRegionsByUserName(username);
	}

	@Override
	public List<Project> findAssignedProjectsByUserName(String username) {
		return userRegionRoleRepository.findAssignedProjectsByUserName(username);
	}

	@Override
	public void delete(Integer id) {
		userRegionRoleRepository.delete(id);
		
	}

}
