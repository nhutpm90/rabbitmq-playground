package com.rabbitmq.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.rabbitmq.demo.api.message.User;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

//	@Autowired
//	private JwtDecoder jwtDecoder;
	
	@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    String username = accessor.getNativeHeader("username").get(0);
                    if("invalid_user".equals(username)) {
                    	
                    } else {
//                    String accessToken = authorization.get(0).split(" ")[1];
//                    Jwt jwt = jwtDecoder.decode(accessToken);
//                    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//                    Authentication authentication = converter.convert(jwt);
//                    accessor.setUser(authentication);
                    	accessor.setUser(new User(username));
                    }
                }
                return message;
            }
        });
    }
}