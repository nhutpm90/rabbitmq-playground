package com.rabbitmq.demo.wsclient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.rabbitmq.demo.api.message.GreetingMessage;

public class GreetingClient {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		WebSocketClient client = new StandardWebSocketClient();

		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		String websocketURI = "ws://localhost:8888/greeting-app";
		StompSessionHandler greetingSessionHandler = new GreetingSessionHandler();
		ListenableFuture<StompSession> sessionAsync = 
				stompClient.connect(websocketURI, greetingSessionHandler);
		
		int count = 1;
		
		StompSession session = sessionAsync.get();
		session.subscribe("/topic/greetings", greetingSessionHandler);
		while (true) {
			String sayHiEndpoint = "/app/say-hi";
			System.out.println("sending message to:: " + sayHiEndpoint);
			session.send(sayHiEndpoint, new GreetingMessage(count++, "dev"));
			Thread.sleep(2000);
		}
	}
}

class GreetingSessionHandler extends StompSessionHandlerAdapter {

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return GreetingMessage.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		GreetingMessage message = (GreetingMessage) payload;
		System.out.println("messages from /topic/greetings:: " + message);
	}
}
