package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Group;
import com.sanyo.quote.repository.GroupRepository;
import com.sanyo.quote.service.GroupService;

@Service("groupService")
@Repository
@Transactional
public class DefaultGroupService implements GroupService{

	@Autowired
	private GroupRepository groupRepository;
	
	@Override
	public List<Group> findAll() {
		return Lists.newArrayList(groupRepository.findAll());
	}

	@Override
	public List<Group> findByUserName(String userName) {
//		return groupRepository.findByUserName(userName);
		return null;
	}

	@Override
	public Group findById(Integer id) {
		return groupRepository.findOne(id);
	}

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Override
	public Page<Group> findAllByPage(Pageable pageable) {
		return groupRepository.findAll(pageable);
	}

	@Override
	public Group findByGroupName(String groupName) {
		// TODO Auto-generated method stub
		return groupRepository.findByGroupName(groupName);
	}

}
