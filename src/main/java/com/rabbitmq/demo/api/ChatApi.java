package com.rabbitmq.demo.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.rabbitmq.demo.api.message.GreetingMessage;

@Controller
public class ChatApi {

	@MessageMapping("/say-hi")
	@SendTo("/topic/greetings")
	public GreetingMessage processMessage(GreetingMessage message) throws Exception {
		Thread.sleep(1000);
		return new GreetingMessage(message.getId(), "Hello from server:: " + message);
	}
}
