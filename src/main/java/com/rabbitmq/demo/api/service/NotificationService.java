package com.rabbitmq.demo.api.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.demo.api.message.EmailMessage;
import com.rabbitmq.demo.api.message.SmsMessage;

@Service
public class NotificationService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static String NOTIFICATION_EXCHANGE = "notification-exchange";
	private static String SMS_QUEUE = "sms-queue";
	private static String SMS_ROUTING_KEY = "rk-sms";
	private static String EMAIL_ROUTING_KEY = "rk-email";

	public void sendSms(SmsMessage smsMessage) {
		rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, SMS_ROUTING_KEY, smsMessage);
	}

	public void sendEmail(EmailMessage emailMessage) {
		rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, EMAIL_ROUTING_KEY, emailMessage);
	}
}

//rabbitTemplate.convertAndSend(SMS_QUEUE, email);
//rabbitTemplate.convertAndSend("queue-name", "message");
//rabbitTemplate.convertAndSend("direct-exchange", "rk", "message");
//rabbitTemplate.convertAndSend("fanout-exchange", "", "message");
//rabbitTemplate.convertAndSend("topic-exchange", "rk.department.hr", "message");