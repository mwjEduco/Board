package com.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.vo.UserVO;

@Repository
public class UserDAO {

	@Autowired
	SqlSession sqlSession;
	
	private static final String Namespace = "com.board.mappers.userMapper";
	
	// 회원 목록
	public List<UserVO> userList() throws Exception{
		
		return sqlSession.selectList(Namespace + ".userList");
	}

	// 로그인
	public Map<String, Object> login(UserVO vo) throws Exception {
		return sqlSession.selectOne(Namespace + ".login", vo);
	}

}
