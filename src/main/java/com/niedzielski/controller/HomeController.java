package com.niedzielski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.service.EmailService;

@RestController
public class HomeController {

	@Autowired
	public EmailService javaMailSender;

	@RequestMapping("/")
	public void home() {

		javaMailSender.sendSimpleMessage("jakub.niedzielski90@gmail.com", "test", "tojesttest");
	}

}
