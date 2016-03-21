package com.sanyo.quote.repository;

import java.util.List;

import com.sanyo.quote.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.domain.UserRegionRole;


public interface UserRegionRoleRepository extends PagingAndSortingRepository<UserRegionRole, Integer> {
	
	@Query("SELECT distinct  p FROM UserRegionRole p JOIN FETCH p.user WHERE p.user.userid = :userid")
	public List<UserRegionRole> findAssignedRegionsByUserId(@Param("userid") Integer userid);
	
	@Query("SELECT distinct  p FROM UserRegionRole p JOIN FETCH p.user WHERE p.user.username = :username")
	public List<UserRegionRole> findAssignedRegionsByUserName(@Param("username") String username);
	
//	@Query("SELECT distinct  ur.region.location.project FROM UserRegionRole ur JOIN FETCH ur.user WHERE ur.user.username = :username")
	public List<Project> findAssignedProjectsByUserName(@Param("username") String username);

	public List<UserRegionRole> findByRegionAndUser(Region region, User user);

}
