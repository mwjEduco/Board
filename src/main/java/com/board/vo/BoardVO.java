package com.board.vo;

import java.sql.Timestamp;

public class BoardVO {
	
	private int board_no;				// 게시판 일련번호
	private String board_title;			// 게시판 제목
	private String board_content;		// 게시판 내용
	private String user_id;				// 최근 작성자
	private Timestamp reg_date;			// 등록일자
	private Timestamp edit_date;		// 수정일자
	private int category_code;			// 카테고리 코드
	private Timestamp board_del;		// 게시판 삭제 유무

	// DB join을 위해 추가
	private String user_name;			// 사용자 이름
	private String category_name;		// 카테고리 이름
	private String category_parent;		// 상위 카테고리
	
	// 파일 업로드를 위해 추가(주석처리해도 정상 동작 되는듯)
//	private List<BoardAttachVO> attachList;
//	
//	public List<BoardAttachVO> getAttachList() {
//		return attachList;
//	}
//	public void setAttachList(List<BoardAttachVO> attachList) {
//		this.attachList = attachList;
//	}
	
	
	public String getCategory_name() {
		return category_name;
	}
	public String getCategory_parent() {
		return category_parent;
	}
	public void setCategory_parent(String category_parent) {
		this.category_parent = category_parent;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public Timestamp getEdit_date() {
		return edit_date;
	}
	public void setEdit_date(Timestamp edit_date) {
		this.edit_date = edit_date;
	}
	public int getCategory_code() {
		return category_code;
	}
	public void setCategory_code(int category_code) {
		this.category_code = category_code;
	}
	public Timestamp getBoard_del() {
		return board_del;
	}
	public void setBoard_del(Timestamp board_del) {
		this.board_del = board_del;
	}
	
}
