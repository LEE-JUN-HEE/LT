package com.lotte.juni.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.lotte.juni.clss.Do;
import com.lotte.juni.clss.Good;
import com.lotte.juni.clss.GoodsAndDo;
import com.lotte.juni.clss.Message;
import com.lotte.juni.clss.Good.Category;
import com.lotte.juni.dao.GoodDao;

@Service
public class LotteService {
	public GoodDao dao;

	public LotteService(GoodDao dao) {
		this.dao = dao;
		dao.Test();
	}
	
	public String ChatBotSay(Message Msg){
		try{
			String result = "";
			if(Msg.getMsg().contains("@") || Msg.getMsg().contains("#")){
				//도움 목적. 찾기나 추천.
				ArrayList<GoodsAndDo> search = Parse(Msg.getMsg());
				for(int i =0; i < search.size(); i++){
					ArrayList<Good> queryresult;
					
					if(search.get(i).doing.getType() == Do.Type.find){
						queryresult = FindGoods(search.get(i));
					}else{
						queryresult = RecommendGoods(search.get(i));
						System.out.println(queryresult);
					}
					System.out.println(queryresult);
					if(queryresult != null){
						for(int j =0; j <queryresult.size(); j++){
							result += ChatBotSaybyGood(queryresult.get(j));
						}
					}
				}
			}
			else{
				
			}
			return result;
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<GoodsAndDo> Parse(String msg){
		ArrayList<GoodsAndDo> returnvalue = new ArrayList<>();
		
		//규칙 : @제품명 #행동
		if(msg.contains("@")){
			String[] list = msg.split("@");
			for(int i = 0; i < list.length; i++){
				GoodsAndDo item = new GoodsAndDo();
				String[] list2 = list[i].split("#");
				
				if(list2.length < 2){
					continue;
				}

				if(Good.isType(list2[0].trim())){ // 카테고리
					item.good = new Good(Category.valueOf(list2[0].trim().toLowerCase()));
				}else{	//특정 제품 명
					item.good = new Good(list2[0]);
				}
				
				if(list2[1].contains("찾")){
					item.doing = new Do(Do.Type.valueOf("find"));
				}else if(list2[i].contains("추천")){
					item.doing = new Do(Do.Type.valueOf("recommend"));
				}
				returnvalue.add(item);
			}
		}
		return returnvalue;
	}
	
	public ArrayList<Good> FindGoods(GoodsAndDo gad){
		ArrayList<Good> result = new ArrayList<Good>();
		Good temp;
		if(gad.good.getCategory() == Category.none){
			//제품명
			for(int i =0; i < GoodDao.Database.length; i++){
				temp = dao.SearchByName(gad.good.getName(), GoodDao.Database[i]);
				if(temp != null){
					temp.setSpace(GoodDao.Database[i]);
					result.add(temp);
				}
			}
		}else{
			//카테고리
			for(int i =0; i < GoodDao.Database.length; i++){
				ArrayList<Good> templ = dao.SearchByCategory(gad.good.getCategory(), GoodDao.Database[i]);
				if(templ != null){
					for(Good tt : templ)
						tt.setSpace(GoodDao.Database[i]);
				}
				result.addAll(templ);
			}
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
		return String.format("<div id = 'bottext'>%s에 %s 있네! 가격은 %d고 주소는<a href = %s target='_blank'>%s</a>이야</div>", good.getSpace(), good.getName(), good.getPrice(), "http://localhost/index.html", "http://localhost/index.html" );
		//아직 연령 안따졌음
	}
	
	public String MappingCategory(){
		return "";
	}
}
