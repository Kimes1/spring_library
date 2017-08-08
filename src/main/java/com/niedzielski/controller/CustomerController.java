package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.model.Customer;
import com.niedzielski.service.CustomerService;

@RestController
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public List<Customer> list() {
		return customerService.getAll();
	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public Customer create(@RequestBody Customer customer) {
		return customerService.addCustomer(customer);
	}
}
