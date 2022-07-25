package com.rabbitmq.demo.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("consume:: queue:: red");
		consume("red");
		System.out.println("consume:: queue:: green");
		consume("green");
	}
	
	static void consume(String queueName) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			System.out.println(String.format("message received:: queue[%s]:val[%s]", queueName, message));
		};
		CancelCallback cancelCallback = consumerTag -> { };
		channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
	}
}

