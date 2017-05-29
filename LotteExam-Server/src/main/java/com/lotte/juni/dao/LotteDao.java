package com.lotte.juni.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class LotteDao {
	NamedParameterJdbcOperations jdbc;
	RowMapper<Lotte> rowMapper;
	private SimpleJdbcInsert insertAction;
	
	public LotteDao(DataSource dataSource){
		jdbc = new NamedParameterJdbcTemplate(dataSource);
		rowMapper = new BeanPropertyRowMapper<>(Lotte.class);
		insertAction = new SimpleJdbcInsert(dataSource).withTableName("?????").usingGeneratedKeyColumns("id");
	}
	
	//public static final 
	public Integer Insert(Lotte lotte){
		SqlParameterSource param = new BeanPropertySqlParameterSource(lotte);
		return insertAction.executeAndReturnKey(param).intValue();
	}
}
