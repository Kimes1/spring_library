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

import com.niedzielski.model.User;
import com.niedzielski.service.UserService;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "customers")
	public List<User> list() {
		return userService.getAllUsers();
	}

	@PostMapping(value = "customers")
	public User create(@RequestBody User customer) {
		return userService.addUser(customer);
	}

	@GetMapping(value = "customers/{id}")
	public User get(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PutMapping(value = "customers/{id}")
	public User update(@PathVariable Long id, @RequestBody User customer) {
		User existingCustomer = userService.getUser(id);
		BeanUtils.copyProperties(customer, existingCustomer);
		return userService.addUser(existingCustomer);
	}

	@DeleteMapping(value = "customers/{id}")
	public User delete(@PathVariable Long id) {
		User existingCustomer = userService.getUser(id);
		userService.deleteUser(id);
		return existingCustomer;
	}

}
