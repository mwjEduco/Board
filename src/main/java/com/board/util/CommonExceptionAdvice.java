package com.board.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	/*
	 * 에러 페이지 처리
	 */
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		logger.info("Exception :::: " + ex.getMessage());
		
		model.addAttribute("exception", ex);
		logger.error("model :: " + model);
		
		return "/common/error/error";
	}
	
	
}
