package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niedzielski.model.User;
import com.niedzielski.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User addCustomer(User customer) {
		return userRepository.saveAndFlush(customer);
	}

	public User get(Long id) {
		return userRepository.findOne(id);
	}

	public User update(Long id, User customer) {
		User existingCustomer = userRepository.findOne(id);
		BeanUtils.copyProperties(customer, existingCustomer);
		return userRepository.saveAndFlush(existingCustomer);
	}

	public User delete(Long id) {
		User existingCustomer = userRepository.findOne(id);
		userRepository.delete(existingCustomer);
		return existingCustomer;
	}
}
