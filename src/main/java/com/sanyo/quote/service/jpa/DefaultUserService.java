
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.User;
import com.sanyo.quote.repository.UserRepository;
import com.sanyo.quote.service.UserService;

@Service("userService")
@Repository
@Transactional
public class DefaultUserService implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly=true)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> findAll() {
		return Lists.newArrayList(userRepository.findAll());
	}

	@Transactional(readOnly=true)
	public User findById(Integer id) {
		return userRepository.findOne(id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User save(User User) {
		return userRepository.save(User);
	}

	@Transactional(readOnly=true)
	public Page<User> findAllByPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(Integer id) {
		userRepository.delete(id);
		
	}
}
