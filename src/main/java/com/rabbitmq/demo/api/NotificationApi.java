package com.rabbitmq.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.demo.api.message.EmailMessage;
import com.rabbitmq.demo.api.message.SmsMessage;
import com.rabbitmq.demo.api.service.NotificationService;

@RestController
public class NotificationApi {
	
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/send-sms")
	public SmsMessage sendSms(@RequestBody SmsMessage sms) {
		this.notificationService.sendSms(sms);
		return sms;
	}
	
	@PostMapping("/send-email")
	public EmailMessage sendEmail(@RequestBody EmailMessage email) {
		this.notificationService.sendEmail(email);
		return email;
	}
}
