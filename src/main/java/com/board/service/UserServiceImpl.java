package com.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dao.UserDAO;
import com.board.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO dao;
	
	// 회원 목록
	@Override
	public List<UserVO> userList() throws Exception {
		return dao.userList();
	}

	// 로그인
	@Override
	public Map<String, Object> login(UserVO vo) throws Exception {
		return dao.login(vo);
	}

}
