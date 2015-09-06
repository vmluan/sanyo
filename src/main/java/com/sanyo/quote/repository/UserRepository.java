package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	@Query("select c from User c where c.username  = :userName")
	User findByUsername(@Param("userName")  String username);
	
	List<User> findByEmail(String username);
}