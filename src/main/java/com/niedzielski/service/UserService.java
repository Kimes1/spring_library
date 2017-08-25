package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niedzielski.exception.UserExistException;
import com.niedzielski.model.User;
import com.niedzielski.repository.UserRepository;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User addUser(User user) throws UserExistException {
		User existingUser = userRepository.findOneByUsernameOrEmail(user.getEmail(), user.getUsername());
		if (existingUser != null) {
			throw new UserExistException("Username or Email already in use");
		}
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		return userRepository.saveAndFlush(user);
	}

	public User getUser(Long id) {
		return userRepository.findOne(id);
	}

	public User getUserByUsername(String userName) {
		return userRepository.findOneByUsername(userName);
	}

	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findOne(id);
		BeanUtils.copyProperties(user, existingUser);
		return userRepository.saveAndFlush(existingUser);
	}

	public User deleteUser(Long id) {
		User existingUser = userRepository.findOne(id);
		userRepository.delete(existingUser);
		return existingUser;
	}
}
