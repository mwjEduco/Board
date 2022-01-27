package com.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.service.UserService;
import com.board.vo.UserVO;

@Controller
@RequestMapping(value = "/user/*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/*
	 * 유저 목록 조회
	 * 페이징
	 * 검색
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public void getUserList(Model model) throws Exception{
		logger.info("유저 목록 조회 페이지 접속");
		
		List<UserVO> userList = userService.userList();
		
		logger.info("userList :: " + userList);
		
		model.addAttribute("userList", userList);
	}
	
	/*
	 * 로그인 화면 접속
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void getLogin() throws Exception{
		logger.info("로그인 화면 접속");
	}
	
	/*
	 * 로그인
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLogin(HttpServletRequest request, UserVO vo, RedirectAttributes rttr) throws Exception{
		logger.info("로그인");
		
		HttpSession session = request.getSession(); //서버에 생성된 세션이 있다면 세션을 반환하고, 없다면 새 세션을 생성하여 반환
		
		Map<String, Object> uvo = userService.login(vo);
		
		if (uvo != null) {								// 로그인 정보 일치

			session.setAttribute("user", uvo);			// session에 사용자의 정보 저장

			return "redirect:/board/list";				// 목록 조회 페이지로 이동
		
		} else {										// 로그인 정보 불일치
			
			int result = 0;
			
			rttr.addFlashAttribute("result", result);
			
			return "redirect:/";				// 로그인 화면 유지
		}
		
	}
	
	/*
	 * 로그아웃
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpServletRequest request) throws Exception{
		logger.info("로그아웃");
		
		HttpSession session = request.getSession();
		
		session.invalidate();	// 세션 삭제
		
		return "redirect:/";	// 로그인 화면으로 이동
	}
	
	
	
}
