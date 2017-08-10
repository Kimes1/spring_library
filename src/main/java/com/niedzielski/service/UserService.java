package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niedzielski.model.User;
import com.niedzielski.repository.UserRepository;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User addUser(User customer) {
		return userRepository.saveAndFlush(customer);
	}

	public User getUser(Long id) {
		return userRepository.findOne(id);
	}

	public User updateUser(Long id, User customer) {
		User existingCustomer = userRepository.findOne(id);
		BeanUtils.copyProperties(customer, existingCustomer);
		return userRepository.saveAndFlush(existingCustomer);
	}

	public User deleteUser(Long id) {
		User existingCustomer = userRepository.findOne(id);
		userRepository.delete(existingCustomer);
		return existingCustomer;
	}
}
