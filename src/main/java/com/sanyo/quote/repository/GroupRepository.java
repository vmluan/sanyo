package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Group;

public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {
//	@Query("select c from Group g where g.groupid  =  :userName")
//	List<Group> findByUserName( @Param("userName") String userName);
	public Group findByGroupName(String groupName);
}
