package com.lotte.juni.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.lotte.juni.clss.Good;
import com.lotte.juni.clss.User;

@Repository
public class GoodDao {
	NamedParameterJdbcOperations jdbc;
	RowMapper<Good> rowMapperLotte;
	RowMapper<User> rowMapperUser;
	private SimpleJdbcInsert insertAction;
	
	public GoodDao(DataSource dataSource){
		jdbc = new NamedParameterJdbcTemplate(dataSource);
		rowMapperLotte = new BeanPropertyRowMapper<>(Good.class);
		rowMapperUser = new BeanPropertyRowMapper<>(User.class);
		insertAction = new SimpleJdbcInsert(dataSource);
	}
	
//	static final String USER_COUNT_BY_ID = "select Count(*) from User where :id";
//	public Integer GetCountUser(String id){
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("id", id);
//		return jdbc.queryForObject(USER_SELECT_BY_ID, param, Integer.class);
//	}
//	
//	static final String USER_SELECT_BY_ID = "select id, age, gender from User where :id";
//	public User SelectUser(String id){
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("id", id);
//		return jdbc.queryForObject(USER_SELECT_BY_ID, param, rowMapperUser);
//	}
	
	//public static final 
	public Integer Insert(Good lotte){
		SqlParameterSource param = new BeanPropertySqlParameterSource(lotte);
		return insertAction.executeAndReturnKey(param).intValue();
	}
	
	static final String GOOD_SELECT_BY_NAME = "SELECT id, category, name, price, count from :db where name = :name";
//	public Good SearchByName(String str){
//		Map<String, String> param = new HashMap();
//		param.put();
//	}
}
