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
	
	public String ChatBotSay(String msg){
		String result = "";
		ArrayList<GoodsAndDo> search = Parse(msg);
		for(int i =0; i < search.size(); i++){
			ArrayList<Good> queryresult = FindGoods(search.get(i));
			for(int j =0; j <queryresult.size(); j++){
				result += ChatBotSaybyGood(queryresult.get(j));
			}
		}
		return result;
	}
	
	public ArrayList<GoodsAndDo> Parse(String msg){
		ArrayList<GoodsAndDo> returnvalue = new ArrayList<>();
		
		//규칙 : @제품명 #행동
		String[] list = msg.split("@");
		
		for(int i = 0; i < list.length; i++){
			GoodsAndDo item = new GoodsAndDo();
			String[] list2 = list[i].split("#");
			
			if(list2.length < 2){
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
			
			returnvalue.add(item);
		}
		return returnvalue;
	}
	
	public ArrayList<Good> FindGoods(GoodsAndDo gad){
		ArrayList<Good> result = new ArrayList<Good>();
		if(gad.good.getCategory() == Category.none){
			//제품명
			for(int i =0; i < GoodDao.Database.length; i++)
				result.add(dao.SearchByName(gad.good.getName(), GoodDao.Database[i]));
		}else{
			//카테고리
			for(int i =0; i < GoodDao.Database.length; i++)
				result.add(dao.SearchByName(gad.good.getName(), GoodDao.Database[i]));
		}
		return result;
	}
	
	public ArrayList<Good> RecommendGoods(GoodsAndDo gad){
		if(gad.good.getCategory() == Category.none){
			//제품명
			for(int i =0; i < GoodDao.Database.length; i++)
				dao.SearchByName(gad.good.getName(), GoodDao.Database[i]);
		}else{
			//카테고리
			for(int i =0; i < GoodDao.Database.length; i++)
				dao.SearchByCategory(gad.good.getCategory(), GoodDao.Database[i]);
		}
		return new ArrayList<Good>();
	}
	
	public String ChatBotSaybyGood(Good good){
		return String.format("%s에 %s 있네! 가격은 %d고 주소는 %s이야\n", good.getSpace(), good.getName(), good.getPrice(), "http://localhost/index.html" );
		//아직 연령 안따졌음
	}
	
	public String MappingCategory(){
		return "";
	}
}
