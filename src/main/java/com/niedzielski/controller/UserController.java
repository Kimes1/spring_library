package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.exception.UserExistException;
import com.niedzielski.model.User;
import com.niedzielski.service.UserService;

@RestController
@RequestMapping("api")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "users")
	public List<User> list() {
		return userService.getAllUsers();
	}

	@PostMapping(value = "users")
	public User create(@RequestBody User customer) throws UserExistException {
		return userService.addUser(customer);
	}

	@GetMapping(value = "users/{id}")
	public User get(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PutMapping(value = "users/{id}")
	public User update(@PathVariable Long id, @RequestBody User customer) throws UserExistException {
		User existingUser = userService.getUser(id);
		BeanUtils.copyProperties(customer, existingUser);
		return userService.addUser(existingUser);
	}

	@DeleteMapping(value = "users/{id}")
	public User delete(@PathVariable Long id) {
		User existingCustomer = userService.getUser(id);
		userService.deleteUser(id);
		return existingCustomer;
	}

}
