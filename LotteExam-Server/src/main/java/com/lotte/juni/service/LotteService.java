package com.lotte.juni.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.lotte.juni.clss.Do;
import com.lotte.juni.clss.Good;
import com.lotte.juni.clss.GoodsAndDo;
import com.lotte.juni.clss.User;
import com.lotte.juni.clss.Good.Category;
import com.lotte.juni.dao.GoodDao;

@Service
public class LotteService {
	public GoodDao dao;

	public LotteService(GoodDao dao) {
		this.dao = dao;
	}

//	public Integer Login(String id) {
//		return dao.GetCountUser(id);
//	}
	
//	public User Init(String id){
//		return dao.SelectUser(id);
//	}
	
	public String ChatBotSay(String msg){
		ArrayList<GoodsAndDo> search = Parse(msg);
		
		return String.format("%S %S", search.get(0).good.getCategory(), search.get(0).doing.toString());
	}
	
	public ArrayList<GoodsAndDo> Parse(String msg){
		ArrayList<GoodsAndDo> returnvalue = new ArrayList<>();
		
		//규칙 : @제품명 #행동
		String[] list = msg.split("@");
		
		for(int i = 0; i < list.length; i++){
			GoodsAndDo item = new GoodsAndDo();
			String[] list2 = list[i].split("#");
			
			if(list2.length < 2){
//				if(list2[0] != ""){
//					item.good = new Good(list[0]);
//					returnvalue.add(item);
//				} 
				continue;
			}

			System.out.println(list2[0].trim());
			if(Good.isType(list2[0].trim())){ // 카테고리
				System.out.println(list2[0].trim());
				item.good = new Good(Category.valueOf(list2[0].trim()));
			}else{	//특정 제품 명
				item.good = new Good(list[0]);
			}
			
			
			if(list2[1].contains("찾")){
				item.doing = new Do(Do.Type.valueOf("find"));
			}else if(list2[i].contains("추천")){
				item.doing = new Do(Do.Type.valueOf("recommend"));
			}
//			for(int j=0; j < returnvalue.size(); j++){
//				if(returnvalue.s)
//			}
			
			returnvalue.add(item);
		}
		return returnvalue;
	}
}
