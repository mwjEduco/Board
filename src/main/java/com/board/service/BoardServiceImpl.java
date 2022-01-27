package com.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.dao.BoardDAO;
import com.board.vo.BoardAttachVO;
import com.board.vo.BoardVO;
import com.board.vo.CategoryVO;
import com.board.vo.PagingVO;
import com.board.vo.summernoteImgVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDAO dao;
	
	// 목록(+페이징)
	@Transactional
	@Override
	public List<BoardVO> listPage(PagingVO pvo) throws Exception {
		
		return dao.listPage(pvo);
	}

	// 글 작성
	@Transactional
	@Override
	public BoardVO write(BoardVO vo) throws Exception {
		return dao.write(vo);
		
	}

	// 상세 페이지
	@Transactional
	@Override
	public BoardVO detail(int board_no) throws Exception {
		
		return dao.detail(board_no);
	}

	// 수정
	@Transactional
	@Override
	public BoardVO modify(BoardVO vo) throws Exception {
		
		return dao.modify(vo);
	}

	// 삭제
	@Transactional
	@Override
	public void delete(int board_no) throws Exception {
		dao.delete(board_no);
	}

	// 카테고리
	@Override
	public List<CategoryVO> category() throws Exception {
		
		return dao.category();
	}

	// 게시물 총 개수
	@Override
	public int totalCount(PagingVO pvo) throws Exception {
		
		return dao.totalCount(pvo);
	}

	// 파일 업로드
	@Override
	public void insertFile(BoardAttachVO fileVO) throws Exception {
		dao.insertFile(fileVO);
	}

	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileDetail(int board_no) throws Exception {
		
		return dao.selectFileDetail(board_no);
	}

	// 첨부파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(int fileNo) throws Exception {
		
		return dao.selectFileInfo(fileNo);
	}

	// summernote 이미지 업로드
	@Override
	public void insertImg(summernoteImgVO imgVO) throws Exception {
		dao.insertImg(imgVO);
	}
	
	// summernote 이미지 조회
	
	
}
 