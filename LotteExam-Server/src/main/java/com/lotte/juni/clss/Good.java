package com.lotte.juni.clss;

public class Good {
	public enum Category{
		cloth,
		cosmetic,
		shoes,
		computer,
		game,
		car,
		home,
		none,
	}
	
	Integer id;
	Category category;
	String name;
	Integer price;
	Integer count;
	String space;
	
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public static boolean isType(String str){
		try{
			return Category.valueOf(str.toLowerCase()) != null;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public Good(){
		this.id = -1;
		this.name = "";
		this.category = Category.none;
		this.price = -1;
		this.count = -1;
	}
	
	public Good(Category category){
		this.id = -1;
		this.name = "";
		this.category = category;
		this.price = -1;
		this.count = -1;
	}

	public Good(String name){
		this.id = -1;
		this.name = name;
		this.category = Category.none;
		this.price = -1;
		this.count = -1;
	}
	
	@Override
	public String toString(){
		return id + "/" +category.toString() + "/" + name + "/" + price + "/" + count;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
