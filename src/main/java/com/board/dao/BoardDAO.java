package com.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.vo.BoardAttachVO;
import com.board.vo.BoardVO;
import com.board.vo.CategoryVO;
import com.board.vo.PagingVO;
import com.board.vo.summernoteImgVO;

@Repository
public class BoardDAO{
	
	@Autowired
	SqlSession sqlSession;
	
	private static final String Namespace = "com.board.mappers.boardMapper";
	
	// 목록(+페이징, +검색)
	public List<BoardVO> listPage(PagingVO pvo) throws Exception {
		
		return sqlSession.selectList(Namespace + ".listPage", pvo);
	}
	
	// 글 작성
	public BoardVO write(BoardVO vo) throws Exception {
		sqlSession.insert(Namespace + ".write", vo);
		return vo;	// board_no를 가지고 리턴
	}

	// 상세 페이지
	public BoardVO detail(int board_no) throws Exception {
		return sqlSession.selectOne(Namespace + ".detail", board_no);
	}

	// 수정
	public BoardVO modify(BoardVO vo) throws Exception {
		sqlSession.update(Namespace + ".modify", vo);
		return vo;	// board_no를 가지고 리턴
	}

	// 삭제
	public void delete(int board_no) throws Exception {
		sqlSession.delete(Namespace + ".delete", board_no);
	}

	// 카테고리
	public List<CategoryVO> category() throws Exception {
		return sqlSession.selectList(Namespace + ".category");
	}
	
	// 게시물 총 개수
	public int totalCount(PagingVO pvo) throws Exception {
		
		return sqlSession.selectOne(Namespace + ".totalCount", pvo);
	}
	
	// 파일 업로드
	public void insertFile(BoardAttachVO fileVO) throws Exception {
		sqlSession.insert(Namespace + ".insertFile", fileVO);
	}
	
	// 첨부파일 조회
	public List<Map<String, Object>> selectFileDetail(int board_no) throws Exception {
	
		return sqlSession.selectList(Namespace + ".selectFileDetail", board_no);
	}
	
	// 첨부파일 다운로드
	public Map<String, Object> selectFileInfo(int fileNo) throws Exception {
	
		return sqlSession.selectOne(Namespace + ".selectFileInfo", fileNo);
	}
	
	// summernote 이미지 업로드
	public void insertImg(summernoteImgVO imgVO) throws Exception{
		sqlSession.insert(Namespace + ".insertImg", imgVO);
	}

	// summernote 이미지 조회

}
