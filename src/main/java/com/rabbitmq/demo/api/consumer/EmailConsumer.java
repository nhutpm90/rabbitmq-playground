package com.rabbitmq.demo.api.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.demo.api.message.EmailMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RabbitListener(queues = "email-queue")
public class EmailConsumer {

	@RabbitHandler
	public void processHandler(EmailMessage emailMessage, Channel channel, Message message) throws IOException {
		log.info("--- processing emailMessage:: begin:: " + emailMessage);
		try {
			log.info("+ processing emailMessage:: " + emailMessage);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			log.info("send email successfully --> basicAck");
		} catch (Exception ex) {
			log.info("something went wrong:: " + ex.getMessage());
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		}
		log.info("--- processing emailMessage:: end");
	}
}
