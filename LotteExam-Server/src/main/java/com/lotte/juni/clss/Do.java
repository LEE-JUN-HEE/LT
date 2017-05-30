package com.lotte.juni.clss;

public class Do {
	public enum Type{
		find,
		recommend,
	}
	
	Type type;
	
	public Do(){
		
	}
	
	public Do(Type type){
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString(){
		return type.toString();
	}
}
