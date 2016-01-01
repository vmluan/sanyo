package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.User;

public interface UserService {

	List<User> findAll();
	
	User findById(Integer id);
	User findByUserName(String userName);
	User save(User user);
	
	Page<User> findAllByPage(Pageable pageable);	
	void delete(Integer id);
}
