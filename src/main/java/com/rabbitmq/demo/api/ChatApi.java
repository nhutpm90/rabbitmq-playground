package com.rabbitmq.demo.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.demo.api.message.GreetingMessage;

@Controller
public class ChatApi {

	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
 
    @GetMapping("/greetings")
    public @ResponseBody GreetingMessage sendGreetings(@RequestBody GreetingMessage message) {
        simpMessagingTemplate.convertAndSend("/topic/greetings", message);
        return message;
    }
    
    @GetMapping("/hello/{username}")
    public @ResponseBody GreetingMessage send(@PathVariable String username,
    		@RequestBody GreetingMessage message) {
    	Map<String, Object> headers = new HashMap<>();
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", message, headers);
        return message;
    }
}
