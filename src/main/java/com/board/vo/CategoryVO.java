package com.board.vo;

public class CategoryVO {
	private int level;					// 카테고리 분류 레벨
	private String category_name;		// 카테고리 이름
	private String category_code;		// 카테고리 코드
	private String category_parent;		// 카테고리 부모 코드
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getCategory_parent() {
		return category_parent;
	}
	public void setCategory_parent(String category_parent) {
		this.category_parent = category_parent;
	}
	
}
