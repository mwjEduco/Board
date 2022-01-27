package com.board.service;

import java.util.List;
import java.util.Map;

import com.board.vo.UserVO;

public interface UserService {
	
	//회원 목록
	public List<UserVO> userList() throws Exception;
	
	// 로그인
	public Map<String, Object> login(UserVO vo) throws Exception;
	
	
}
