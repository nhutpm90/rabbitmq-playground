package com.rabbitmq.demo.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

	public static void main(String[] args) throws IOException, TimeoutException {
		String exchangeName = "topic-exchange";
		publishMessage(exchangeName, "queue.admin", "admin queue message");
		publishMessage(exchangeName, "queue.finance", "finance queue message");
		publishMessage(exchangeName, "queue.marketing", "marketing queue message");
		System.out.println("done...");
	}
	
	static void publishMessage(String exchangeName, String routingKey, String message) 
			throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		channel.close();
		connection.close();
	}
}



