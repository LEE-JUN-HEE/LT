package com.lotte.juni.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lotte.juni.TextFormat;
import com.lotte.juni.clss.Message;
import com.lotte.juni.service.LotteService;

@RestController
public class LotteController {
	LotteService service;

	// 소켓작업

	public LotteController(LotteService service) {
		this.service = service;
	}

	// @PostMapping("/Login")
	// public Integer Login(@RequestBody String id){
	// if(service.Login(id) != 0){
	// return 1;
	// }
	// else return 0;
	// }

	@PostMapping("/init")
	public String Init(@RequestBody Message Msg) {
		try {
			return String.format(TextFormat.GetStringformat(Msg, TextFormat.Type.hello), Msg.getAge());
		} catch (Exception e) {
			System.out.println(e);
			return String.format(TextFormat.emptyMsg, Msg.getMsg());
		}
	}

	@PostMapping("/mysend")
	public String Send(@RequestBody Message msg) {
		try {
			return service.ChatBotSay(msg);
		} catch (Exception e) {
			System.out.println(e);
			return String.format(TextFormat.emptyMsg, msg.getMsg());
		}
	}
}
