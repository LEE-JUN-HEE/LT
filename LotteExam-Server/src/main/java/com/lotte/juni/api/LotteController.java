package com.lotte.juni.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lotte.juni.clss.Message;
import com.lotte.juni.service.LotteService;

@RestController
public class LotteController {
	LotteService service;
	
	public Message tempUser;
	//소켓작업
	
	public LotteController(LotteService service){
		this.service = service;
	}
	
//	@PostMapping("/Login")
//	public Integer Login(@RequestBody String id){
//		if(service.Login(id) != 0){
//			return 1;
//		}
//		else return 0;
//	}
	
	@PostMapping("/init")
	public String Init(@RequestBody Message user){
		tempUser = user;
		return String.format("%d세의 %s 고객님! 환영합니다~ 저는 챗봇입니다! 무엇을 도와드릴까요?", tempUser.getAge(), tempUser.getGender() == 0 ? "남성" : "여성");
	}
	
	@PostMapping("/mysend")
	public String Send(@RequestBody Message msg){
		return service.ChatBotSay(msg);
	}
}
