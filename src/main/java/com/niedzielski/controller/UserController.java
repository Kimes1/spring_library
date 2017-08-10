package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.model.User;
import com.niedzielski.service.UserService;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public List<User> list() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "customers", method = RequestMethod.POST)
	public User create(@RequestBody User customer) {
		return userService.addUser(customer);
	}

	@RequestMapping(value = "customers/{id}", method = RequestMethod.GET)
	public User get(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@RequestMapping(value = "customers/{id}", method = RequestMethod.PUT)
	public User update(@PathVariable Long id, @RequestBody User customer) {
		User existingCustomer = userService.getUser(id);
		BeanUtils.copyProperties(customer, existingCustomer);
		return userService.addUser(existingCustomer);
	}

	@RequestMapping(value = "customers/{id}", method = RequestMethod.DELETE)
	public User delete(@PathVariable Long id) {
		User existingCustomer = userService.getUser(id);
		userService.deleteUser(id);
		return existingCustomer;
	}

}
