package com.rabbitmq.demo.api.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.demo.api.message.EmailMessage;
import com.rabbitmq.demo.api.message.SmsMessage;
import com.rabbitmq.demo.config.RabbitMQConfig;

@Service
public class NotificationService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendSms(SmsMessage smsMessage) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, RabbitMQConfig.SMS_ROUTING_KEY, smsMessage);
	}

	public void sendEmail(EmailMessage emailMessage) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE, RabbitMQConfig.EMAIL_ROUTING_KEY, emailMessage);
	}
}

//rabbitTemplate.convertAndSend(SMS_QUEUE, email);
//rabbitTemplate.convertAndSend("queue-name", "message");
//rabbitTemplate.convertAndSend("direct-exchange", "rk", "message");
//rabbitTemplate.convertAndSend("fanout-exchange", "", "message");
//rabbitTemplate.convertAndSend("topic-exchange", "rk.department.hr", "message");