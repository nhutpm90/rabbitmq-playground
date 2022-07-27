package com.rabbitmq.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config
			.setApplicationDestinationPrefixes("/app")
			.enableStompBrokerRelay("/topic", "/queue")
			.setRelayHost("172.16.3.15")
			.setRelayPort(61613)
			.setClientLogin("guest")
			.setClientPasscode("guest");
		config.setUserDestinationPrefix("/users");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/greeting-app").setAllowedOriginPatterns("*").withSockJS();
		registry.addEndpoint("/greeting-app").setAllowedOriginPatterns("*");
	}
	
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.enableSimpleBroker("/topic");
//		config.setApplicationDestinationPrefixes("/app");
//	}
//	
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/greeting-app").setAllowedOriginPatterns("*").withSockJS();
//		registry.addEndpoint("/greeting-app").setAllowedOriginPatterns("*");
//	}
	
//	@Override
//	public void configureClientInboundChannel(ChannelRegistration registration) {
//		registration.interceptors(new UserInterceptor());
//	}
}
