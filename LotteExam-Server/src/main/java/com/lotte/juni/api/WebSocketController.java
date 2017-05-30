package com.lotte.juni.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
	@MessageMapping("/bot")
	@SendTo("/chatbot/lotte")
	public String hi(String message){
		System.out.println(message);
		return message;
	}
}
