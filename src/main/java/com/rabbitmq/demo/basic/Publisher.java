package com.rabbitmq.demo.basic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

	public static void main(String[] args) throws IOException, TimeoutException {
		publishMessage();
		System.out.println("done...");
	}
	
	static void publishMessage() {
		Gson gs = new Gson();
		try {
			Thread t = new Thread(() -> {
				try {
					ConnectionFactory factory = new ConnectionFactory();
					Connection connection = factory.newConnection();
					Channel channel = connection.createChannel();
					int i = 0;
					String queueName = "queue-01";
					while(true && i++ < 10) {
						Thread.sleep(100);
						String message = "publish message " + i;
						Map<String, String> data = new HashMap<>();
						data.put("id", String.valueOf(i));
						data.put("data", message);
						System.out.println(message);
						channel.basicPublish("", queueName, null, gs.toJson(data).getBytes());
					}
					channel.close();
					connection.close();
				} catch (Exception ex) { }
			});
			t.start();
			t.join();
		} catch(Exception ex) { }
	}
}

