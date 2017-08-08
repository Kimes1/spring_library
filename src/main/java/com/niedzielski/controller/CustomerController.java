package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.model.Customer;
import com.niedzielski.service.CustomerService;

@RestController
@RequestMapping("api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public List<Customer> list() {
		return customerService.getAll();
	}

	@RequestMapping(value = "customers", method = RequestMethod.POST)
	public Customer create(@RequestBody Customer customer) {
		return customerService.addCustomer(customer);
	}

	@RequestMapping(value = "customers/{id}", method = RequestMethod.GET)
	public Customer get(@PathVariable Long id) {
		return customerService.get(id);
	}

	@RequestMapping(value = "customers/{id}", method = RequestMethod.PUT)
	public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
		Customer existingCustomer = customerService.get(id);
		BeanUtils.copyProperties(customer, existingCustomer);
		return customerService.addCustomer(existingCustomer);
	}

	@RequestMapping(value = "customers/{id}", method = RequestMethod.DELETE)
	public Customer delete(@PathVariable Long id) {
		Customer existingCustomer = customerService.get(id);
		customerService.delete(id);
		return existingCustomer;
	}
}
