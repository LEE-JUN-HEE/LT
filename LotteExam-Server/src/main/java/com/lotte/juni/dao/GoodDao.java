package com.lotte.juni.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

@Repository
public class GoodDao {
	NamedParameterJdbcOperations jdbc;
	RowMapper<Good> rowMapperGood;
	RowMapper<Message> rowMapperUser;
	private SimpleJdbcInsert insertAction;

	static final public String[] Database = { "dotcom", "mart", "depart" };

	public GoodDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
		rowMapperGood = new BeanPropertyRowMapper<>(Good.class);
		rowMapperUser = new BeanPropertyRowMapper<>(Message.class);
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

	static final String TEST = "SELECT * from dotcom;";
	public List<Good> Test(){
		Map<String, String> param = new HashMap<String, String>();
		param.put("category", "cloth");
		System.out.println("cloth" == Category.cloth.toString());
		System.out.println(jdbc.query(String.format(GOOD_SELECT_BY_CATEGORY, "dotcom"), param, rowMapperGood).size());
		return jdbc.query(TEST, param, rowMapperGood);
	}
}
