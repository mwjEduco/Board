package com.board.vo;

import java.sql.Timestamp;

public class UserVO {
	
	private String user_id;				// 아이디
	private String user_pw;				// 비밀번호
	private String user_name;			// 사용자 이름
	private String tel;					// 연락처
	private Timestamp user_reg_date;	// 회원가입일자
	private Timestamp user_del;			// 사용자 삭제 유무(날짜)
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Timestamp getUser_reg_date() {
		return user_reg_date;
	}
	public void setUser_reg_date(Timestamp user_reg_date) {
		this.user_reg_date = user_reg_date;
	}
	public Timestamp getUser_del() {
		return user_del;
	}
	public void setUser_del(Timestamp user_del) {
		this.user_del = user_del;
	}
	
}
