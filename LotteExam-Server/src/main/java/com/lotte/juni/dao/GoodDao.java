package com.lotte.juni.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.lotte.juni.clss.Good;
import com.lotte.juni.clss.Good.Category;
import com.lotte.juni.clss.Message;
import com.lotte.juni.clss.Recommend;

@Repository
public class GoodDao {
	NamedParameterJdbcOperations jdbc;
	RowMapper<Good> rowMapperGood;
	RowMapper<Recommend> rowMapperRecommend;
	RowMapper<Integer> rowMapperInteger;
	private SimpleJdbcInsert insertAction;

	static final public String[] Database = { "dotcom", "mart", "depart" };
	static final public String[] DatabaseRecommend = { "young", "middle", "elder" };

	public GoodDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
		rowMapperGood = new BeanPropertyRowMapper<>(Good.class);
		rowMapperRecommend = new BeanPropertyRowMapper<>(Recommend.class);
		rowMapperInteger = new BeanPropertyRowMapper<>(Integer.class);
		insertAction = new SimpleJdbcInsert(dataSource);
	}

	// static final String USER_COUNT_BY_ID = "select Count(*) from User where
	// :id";
	// public Integer GetCountUser(String id){
	// Map<String, String> param = new HashMap<String, String>();
	// param.put("id", id);
	// return jdbc.queryForObject(USER_SELECT_BY_ID, param, Integer.class);
	// }
	//
	// static final String USER_SELECT_BY_ID = "select id, age, gender from User
	// where :id";
	// public User SelectUser(String id){
	// Map<String, String> param = new HashMap<String, String>();
	// param.put("id", id);
	// return jdbc.queryForObject(USER_SELECT_BY_ID, param, rowMapperUser);
	// }

	// public static final
	
	public Integer Insert(Good lotte) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(lotte);
		return insertAction.executeAndReturnKey(param).intValue();
	}

	static final String GOOD_SELECT_BY_NAME = "SELECT id, category, name, price, count from %s where name = :name;";

	public Good SearchByName(String name, String db) {
		try {
			 MapSqlParameterSource paramSource = new MapSqlParameterSource();
			 paramSource.addValue("name", name);
			 return jdbc.queryForObject(String.format(GOOD_SELECT_BY_NAME, db), paramSource, rowMapperGood);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	static final String GOOD_SELECT_BY_CATEGORY = "SELECT id, category, name, price, count from %s where category = :category;";
	public ArrayList<Good> SearchByCategory(Category category, String db) {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("category", category.toString());
			ArrayList<Good> list = new ArrayList<Good>();
			list.addAll(jdbc.query(String.format(GOOD_SELECT_BY_CATEGORY, db), param, rowMapperGood));
			return list;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	static final String GOOD_SELECT_BY_ID = "SELECT id, category, name, price, count from %s where id = :id;";
	public Good SearchById(Integer id, String db){
		try{
			 MapSqlParameterSource paramSource = new MapSqlParameterSource();
			 paramSource.addValue("id", id);
			 return jdbc.queryForObject(String.format(GOOD_SELECT_BY_ID, db), paramSource, rowMapperGood);
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	static final String SELECT_FROM_RECOMMEND = "SELECT gender, type, id, count from %s where gender = :gender AND type = :type AND id = :id";
	public Recommend SelectfromRecommend(String db, Recommend recommend){
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("gender", recommend.getGender());
			param.put("type", recommend.getType());
			param.put("id", recommend.getId());
			return jdbc.queryForObject(String.format(SELECT_FROM_RECOMMEND, db), param, rowMapperRecommend);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	static final String INSERT_INTO_RECOMMEND = "Insert into %s (gender, type, id, count) values (:gender, :type, :id, :count)";
	public Integer InsertToRecommend(String db, Recommend recommend){
		try {
			SqlParameterSource param = new BeanPropertySqlParameterSource(recommend);
			return jdbc.update(String.format(INSERT_INTO_RECOMMEND, db), param);
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	static final String UPDATE_RECOMMEND = "Update %s set count = :count where gender = :gender and type = :type and id = :id";
	public Integer UpdateRecommend(String db, Recommend recommend){
		try {
			SqlParameterSource param = new BeanPropertySqlParameterSource(recommend);
			return jdbc.update(String.format(UPDATE_RECOMMEND, db), param);
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	static final String SELECT_RECOMMEND_BY_TYPE = "Select id from %s where count = (select max(count) from %s where gender = :gender) and type = :type and gender = :gender";
	public ArrayList<Integer> SelectfromRecommendbyType(String db, Message Msg, String type){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gender", Msg.getGender());
		param.put("type", type);
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.addAll(jdbc.queryForList(String.format(SELECT_RECOMMEND_BY_TYPE, db, db), param, Integer.class));
		return list;
	}
}
