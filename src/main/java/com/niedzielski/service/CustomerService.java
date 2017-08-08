package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

	public Customer get(Long id) {
		return customerRepository.findOne(id);
	}

	public Customer update(Long id, Customer customer) {
		Customer existingCustomer = customerRepository.findOne(id);
		BeanUtils.copyProperties(customer, existingCustomer);
		return customerRepository.saveAndFlush(existingCustomer);
	}

	public Customer delete(Long id) {
		Customer existingCustomer = customerRepository.findOne(id);
		customerRepository.delete(existingCustomer);
		return existingCustomer;
	}
}
