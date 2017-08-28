package com.niedzielski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public JavaMailSender javaMailSender;
	public SimpleMailMessage simpleMailMessage;

	@Autowired
	public EmailService(JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage) {
		this.javaMailSender = javaMailSender;
		this.simpleMailMessage = simpleMailMessage;
	}

	public void sendSimpleMessage(String to, String subject, String text) {
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}
}