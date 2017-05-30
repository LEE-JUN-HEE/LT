package com.lotte.juni.clss;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.lotte.juni.clss.Good.Category;
import com.lotte.juni.dao.GoodDao;

@Service
public class StringParser {
	public GoodDao dao;
	
	public StringParser(GoodDao dao){
		this.dao = dao;
	}
	
	public ArrayList<GoodsAndDo> Parse(String msg){
		ArrayList<GoodsAndDo> returnvalue = new ArrayList<>();
		
		//규칙 : @제품명 #행동
		String[] list = msg.split("@");
		for(int i = 0; i < list.length; i++){
			GoodsAndDo item = new GoodsAndDo();
			String[] list2 = msg.split("#");
			
			if(Good.isType(list2[0])){ // 카테고리
				item.good = new Good(Category.valueOf(list[0]));
			}else{	//특정 제품 명
				item.good = new Good(list[0]);
			}
			
			if(list2[1].contains("찾")){
				item.doing = new Do(Do.Type.valueOf("find"));
			}else if(list2[i].contains("추천")){
				item.doing = new Do(Do.Type.valueOf("recommend"));
			}
			
			returnvalue.add(item);
		}
		
		return returnvalue;
	}
}
