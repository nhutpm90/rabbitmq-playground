package com.rabbitmq.demo.api.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.demo.api.message.SmsMessage;
import com.rabbitmq.demo.api.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RabbitListener(queues = "sms-queue")
public class SmsConsumer {

	@Autowired
	private NotificationService notificationService;
	
	@RabbitHandler
	public void processHandler(SmsMessage smsMessage, Channel channel, Message message) throws IOException {
		log.info("--- processing smsMessage:: begin:: " + smsMessage);
		try {
//			String exchange = message.getMessageProperties().getReceivedExchange();
//			String routingKey = message.getMessageProperties().getReceivedRoutingKey();
			String phoneNumber = smsMessage.getPhoneNumber();
			if (phoneNumber == null || phoneNumber.isEmpty()) {
				log.info("+ empty phoneNumber --> reject message");
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
			} else {
				boolean sendSms = sendSms(smsMessage);
				if(sendSms) {
					// send sms successfully
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					log.info("+ send sms successfully --> basicAck");
				} else {
					int retryCount = smsMessage.getRetryCount();
					log.info("+ something went wrong --> retrying --> retryCount:: " + retryCount);
					if(retryCount < 3) {
						smsMessage.increaseRetryCount();
						channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
						// resend message to the queue for retrying...
						notificationService.sendSms(smsMessage);
					} else {
						log.info("+ sexceed max retry, reject message --> basicReject");
						// exceed max retry, reject message
						channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
					}
				}
			}
		} catch (Exception ex) {
			log.info("+ something went wrong:: " + ex.getMessage());
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		}
		log.info("--- processing smsMessage:: end");
	}
	
	private boolean sendSms(SmsMessage smsMessage) {
		boolean result = false;
		String phoneNumber = smsMessage.getPhoneNumber();
		if("xxx".equals(phoneNumber)) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
}
