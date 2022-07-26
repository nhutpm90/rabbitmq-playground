package com.rabbitmq.demo.competing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

	public static void main(String[] args) throws IOException, TimeoutException {
		final String consumerName = "consumer 01";
		final long sleep = 5000;
		System.out.println("start " + consumerName);
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.16.3.15");
		factory.setVirtualHost("/");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.basicQos(1);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			try {
				System.out.println(consumerName + ":: processing message:: " + message + " - took: "+sleep+"s");
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		};
		CancelCallback cancelCallback = consumerTag -> { };
		
		String queueName = "test-queue-01";
		channel.basicConsume(queueName, false, deliverCallback, cancelCallback);
	}
}

