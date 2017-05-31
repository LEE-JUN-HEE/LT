package com.lotte.juni.service;

import static org.mockito.Matchers.intThat;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.lotte.juni.TextFormat;
import com.lotte.juni.TextFormat.Type;
import com.lotte.juni.clss.Do;
import com.lotte.juni.clss.Good;
import com.lotte.juni.clss.Good.Category;
import com.lotte.juni.clss.GoodsAndDo;
import com.lotte.juni.clss.Message;
import com.lotte.juni.clss.Recommend;
import com.lotte.juni.dao.GoodDao;

@Service
public class LotteService {
	public GoodDao dao;

	public LotteService(GoodDao dao) {
		this.dao = dao;
	}

	public String ChatBotSay(Message Msg) {
		try {
			String result = "";
			if (Msg.getMsg().contains("@") || Msg.getMsg().contains("#")) {
				// 도움 목적. 찾기나 추천.
				ArrayList<GoodsAndDo> search = Parse(Msg);
				for (int i = 0; i < search.size(); i++) {
					if (search.get(i).doing.getType() == Do.Type.find) {
						HashMap<Integer, Good> queryresult;
						queryresult = FindGoods(Msg, search.get(i));
						if (queryresult != null) {
							Object[] keys = queryresult.keySet().toArray();
							for (int j = 0; j < keys.length; j++) {
								result += ChatBotSaybyGood(Msg, queryresult.get(keys[j]));
							}
						}
					} else {
						result += ChatBotSaybyRecommend(Msg ,RecommendGoods(Msg, search.get(i), "find"), "find");
						result += ChatBotSaybyRecommend(Msg, RecommendGoods(Msg, search.get(i), "buy"), "buy");
					}
				}
				return result == "" ? String.format(TextFormat.emptyMsg, Msg.getMsg()) : result;
			} else {
				return String.format(TextFormat.blah, Msg.getMsg());
			}
		} catch (Exception e) {
			System.out.println(e);
			return String.format(TextFormat.emptyMsg, Msg.getMsg());
		}
	}
	
	public String ChatBotSaybyGood(Message Msg, Good good) {
		try{
			String space = "";
			for (int i = 0; i < good.getSpace().size(); i++)
				space += "[" + good.getSpace().get(i) + "]";
			return String.format(TextFormat.GetStringformat(Msg, Type.find), space, good.getName(), good.getPrice(),
					"http://localhost/index.html", "http://localhost/index.html");
		}
		catch (Exception e){
			System.out.println(e);
			return null;
		}
	}

	public String ChatBotSaybyRecommend(Message Msg, HashMap<Integer, Good> Reccomendlist, String type){
		try{

			if(Reccomendlist == null || Reccomendlist.size() == 0)
				return String.format(TextFormat.Man.recoomendEmpty, Msg.getAge(), type == "find" ? "많이 팔린것" : "많이 찾은것" );
			else{
				String result = "";
				Object[] keys = Reccomendlist.keySet().toArray();
				for(int i =0; i < keys.length; i++){
					result += String.format(TextFormat.GetStringformat(Msg, Type.recommend), Msg.getAge(), Reccomendlist.get(keys[i]).getName(), type == "find" ? "찾아봤네" : "샀네", "http://localhost/index.html", "http://localhost/index.html" );
				}
				return result;
			}
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<GoodsAndDo> Parse(Message msg) {
		try{
			ArrayList<GoodsAndDo> returnvalue = new ArrayList<>();

			// 규칙 : @제품명 #행동
			if (msg.getMsg().contains("@")) {
				String[] list = msg.getMsg().split("@");
				for (int i = 0; i < list.length; i++) {
					GoodsAndDo item = new GoodsAndDo();
					String[] list2 = list[i].split("#");

					if (list2.length < 2) {
						continue;
					}

					if (Good.isType(list2[0].trim())) { // 카테고리
						item.good = new Good(Category.valueOf(list2[0].trim().toLowerCase()));
					} else { // 특정 제품 명
						item.good = new Good(list2[0]);
					}

					if (list2[1].contains("찾")) {
						item.doing = new Do(Do.Type.valueOf("find"));
					} else if (list2[i].contains("추천")) {
						item.doing = new Do(Do.Type.valueOf("recommend"));
					}
					returnvalue.add(item);
				}
			}
			return returnvalue;
		}
		catch( Exception e){
			System.out.println(e);
			return null;
		}
		
	}

	public HashMap<Integer, Good> FindGoods(Message Msg, GoodsAndDo gad) {
		try{
			HashMap<Integer, Good> result = new HashMap<Integer, Good>();
			Good temp;
			if (gad.good.getCategory() == Category.none) {
				// 제품명
				for (int i = 0; i < GoodDao.Database.length; i++) {
					temp = dao.SearchByName(gad.good.getName(), GoodDao.Database[i]);
					if (temp != null) {
						if (result.containsKey(temp.getId())) {
							result.get(temp.getId()).addSpace(GoodDao.Database[i]);
						} else {
							temp.setSpace(new ArrayList<String>());
							temp.addSpace(GoodDao.Database[i]);
							result.put(temp.getId(), temp);
						}
					}
				}
			} else {
				// 카테고리
				for (int i = 0; i < GoodDao.Database.length; i++) {
					ArrayList<Good> templ = dao.SearchByCategory(gad.good.getCategory(), GoodDao.Database[i]);
					if (templ != null) {
						for (int j = 0; j < templ.size(); j++) {
							if (result.containsKey(templ.get(j).getId())) {
								result.get(templ.get(j).getId()).addSpace(GoodDao.Database[i]);
							} else {
								templ.get(j).setSpace(new ArrayList<String>());
								templ.get(j).addSpace(GoodDao.Database[i]);
								result.put(templ.get(j).getId(), templ.get(j));
							}
						}
					}
				}
			}
			InsertTo(Msg, result, "find");
			return result;
		}
		catch (Exception e){
			System.out.println(e);
			return null;
		}
	}

	public HashMap<Integer, Good> RecommendGoods(Message Msg, GoodsAndDo gad, String type){
		try{
			HashMap<Integer, Good> result = new HashMap<Integer, Good>();
			ArrayList<Integer> templ = dao.SelectfromRecommendbyType(GoodDao.DatabaseRecommend[Msg.getAge() / 30], Msg, type);
			if(templ != null){
				for(int j = 0; j < templ.size(); j++ ){
					if(result.containsKey(templ.get(j))){
						continue;
					}
					else{
						Good recommendgood;		
						for(int k = 0; k < GoodDao.Database.length; k++){
							recommendgood = dao.SearchById(templ.get(j), GoodDao.Database[k]);
							if(recommendgood != null){
								if(result.containsKey(templ.get(j))){
									result.get(templ.get(j)).addSpace(GoodDao.Database[k]);
								}
								else{
									result.put(recommendgood.getId(), recommendgood);
								}
							}
						}
					}
				}
			}
			return result;
		}
		catch (Exception e){
			System.out.println(e);
			return null;
		}
		
		
//		if(gad.good.getCategory() == Category.none){
//			//제품명
//			
//			for(int i =0; i< result.ke)
//		}else{
//			//카테고리
//			for(int i =0; i < GoodDao.Database.length; i++)
//				dao.SearchByCategory(gad.good.getCategory(), GoodDao.Database[i]);
//		}
	}

	public String MappingCategory() {
		return "";
	}

	public void InsertTo(Message Msg, HashMap<Integer, Good> goodlist, String method) {
		try{
			Object[] keys = goodlist.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				Good temp = goodlist.get(keys[i]);
				Recommend target = new Recommend(Msg.getGender() % 2, method, temp.getId());
				Recommend temp2 = dao.SelectfromRecommend(GoodDao.DatabaseRecommend[Msg.getAge() / 30],
						new Recommend(Msg.getGender() % 2, method, temp.getId()));
				if (temp2 != null) {
					temp2.setCount(temp2.getCount() + 1);
					dao.UpdateRecommend(GoodDao.DatabaseRecommend[Msg.getAge() / 30], temp2);
				} else {
					dao.InsertToRecommend(GoodDao.DatabaseRecommend[Msg.getAge() / 30], target);
				}
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
}
