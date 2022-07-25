package com.rabbitmq.demo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String NOTIFICATION_EXCHANGE = "notification-exchange";
	public static final String SMS_QUEUE = "sms-queue";
	public static final String EMAIL_QUEUE = "email-queue";
	public static final String SMS_ROUTING_KEY = "rk-sms";
	public static final String EMAIL_ROUTING_KEY = "rk-email";
	
	@Bean
	Declarables directExchangeBindings() {
		Queue smsQueue = new Queue(SMS_QUEUE, true);
		Queue emailQueue = new Queue(EMAIL_QUEUE, true);

		DirectExchange directExchange = new DirectExchange(NOTIFICATION_EXCHANGE, true, false);

		return new Declarables(smsQueue, emailQueue, directExchange,
				BindingBuilder.bind(smsQueue).to(directExchange).with(SMS_ROUTING_KEY),
				BindingBuilder.bind(emailQueue).to(directExchange).with(EMAIL_ROUTING_KEY));
	}
	
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("172.16.3.15");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }
    
	@Bean
	AmqpTemplate template(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}
	
//	@Bean
//	@Qualifier("smsQueue") 
//	Queue smsQueue() {
//		return new Queue(SMS_QUEUE, true, false, false);
//	}
//	
//	@Bean
//	@Qualifier("emailQueue") 
//	Queue emailQueue() {
//		return new Queue(EMAIL_QUEUE, true, false, false);
//	}
//	
//	@Bean
//	DirectExchange exchange() {
//		return new DirectExchange(NOTIFICATION_EXCHANGE);
//	}
//
//	@Bean
//	Binding bindingSmsQueue(@Qualifier("smsQueue") Queue queue, DirectExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(SMS_ROUTING_KEY);
//	}
//
//	@Bean
//	Binding bindingEmailQueue(@Qualifier("emailQueue") Queue queue, DirectExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(EMAIL_ROUTING_KEY);
//	}

}