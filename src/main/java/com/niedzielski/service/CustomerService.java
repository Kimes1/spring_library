package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niedzielski.model.Customer;
import com.niedzielski.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	public Customer addCustomer(Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}
}
