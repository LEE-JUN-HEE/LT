package com.lotte.juni.service;

import com.lotte.juni.dao.LotteDao;
import org.springframework.stereotype.Service;

@Service
public class LotteService {
	public LotteDao dao;
	
	public LotteService(LotteDao dao){
		this.dao = dao;
	}
}
