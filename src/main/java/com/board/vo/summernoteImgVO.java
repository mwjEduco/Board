package com.board.vo;

public class summernoteImgVO {
	private int img_no;				// 이미지 번호
	private String img_uuid;		// 이미지 uuid
	private String img_uploadPath;	// 이미지 업로드 경로
	private String img_fileName;	// 이미지 파일명
	private int board_no;			// 게시판 번호
	
	public int getImg_no() {
		return img_no;
	}
	public void setImg_no(int img_no) {
		this.img_no = img_no;
	}
	public String getImg_uuid() {
		return img_uuid;
	}
	public void setImg_uuid(String img_uuid) {
		this.img_uuid = img_uuid;
	}
	public String getImg_uploadPath() {
		return img_uploadPath;
	}
	public void setImg_uploadPath(String img_uploadPath) {
		this.img_uploadPath = img_uploadPath;
	}
	public String getImg_fileName() {
		return img_fileName;
	}
	public void setImg_fileName(String img_fileName) {
		this.img_fileName = img_fileName;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	
	
	
}
