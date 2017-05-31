package com.lotte.juni.clss;

public class Recommend {
	int gender;
	String type;
	int id;
	int count;

	public Recommend(){
		
	}
	
	public Recommend(Integer gender, String method, Integer id) {
		// TODO Auto-generated constructor stub
		this.gender = gender;
		this.type = method;
		this.id = id;
		this.count = 1;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
