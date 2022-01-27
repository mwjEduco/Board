package com.board.service;

import java.util.List;
import java.util.Map;

import com.board.vo.BoardAttachVO;
import com.board.vo.BoardVO;
import com.board.vo.CategoryVO;
import com.board.vo.PagingVO;
import com.board.vo.summernoteImgVO;

public interface BoardService {
	
	// 목록(+페이징, +검색)
	public List<BoardVO> listPage(PagingVO pvo) throws Exception;
	
	// 글 작성
	public BoardVO write(BoardVO vo) throws Exception;
	
	// 상세 페이지
	public BoardVO detail(int board_no) throws Exception;
	
	// 수정
	public BoardVO modify(BoardVO vo) throws Exception;
	
	// 삭제
	public void delete(int board_no) throws Exception;
	
	// 카테고리
	public List<CategoryVO> category() throws Exception;
	
	// 게시물 총 개수
	public int totalCount(PagingVO pvo) throws Exception;
	
	// 파일 업로드
	public void insertFile(BoardAttachVO fileVO) throws Exception;
	
	// 첨부파일 조회
	public List<Map<String, Object>> selectFileDetail(int board_no) throws Exception;
	
	// 첨부파일 다운로드
	public Map<String, Object> selectFileInfo(int fileNo) throws Exception;
	
	// summernote 이미지 업로드
	public void insertImg(summernoteImgVO imgVO) throws Exception;
	
	
	// summernote 이미지 조회
	
}
