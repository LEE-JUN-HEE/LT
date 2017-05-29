package com.lotte.juni.api;

import org.springframework.web.bind.annotation.RestController;

import com.lotte.juni.service.LotteService;

@RestController
public class LotteController {
	LotteService service;
	
	public LotteController(LotteService service){
		this.service = service;
	}
}
