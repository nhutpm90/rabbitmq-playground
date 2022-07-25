//package com.rabbitmq.demo.api.consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//import com.rabbitmq.demo.api.message.EmailMessage;
//import com.rabbitmq.demo.api.message.SmsMessage;
//
//@Service
//public class NotificationConsumer {
//
//	@RabbitListener(queues = "sms-queue")
//	public void processSmsMessage(SmsMessage smsMessage) {
//		System.out.println(String.format("process sms message:: %s", smsMessage));
//	}
//	
//	@RabbitListener(queues = "email-queue")
//	public void processEmailMessage(EmailMessage emailMessage) {
//		System.out.println(String.format("process email message:: %s", emailMessage));
//	}
//}
