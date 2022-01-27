package com.board.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.board.service.BoardService;

@Controller
@RequestMapping("/common")
public class CommonController{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	private static final String FILE_SERVER_PATH = "D:\\upload";
	
	@Autowired
	private BoardService service;
	
	/*
	 * 첨부파일 다운로드
	 */
	@RequestMapping("/download")
	public ModelAndView download(@RequestParam HashMap<Object, Object> hashMap, ModelAndView mv) throws Exception {
		int fileNo = Integer.parseInt((String) hashMap.get("file_no"));	// detail.jsp에서 보낸 파라메터를 받음.
		logger.info("file_no :::: " + fileNo);
		
		// 파일상세정보 조회
		Map<String, Object> resultMap = service.selectFileInfo(fileNo);
		logger.info("resultMap :::: " + resultMap);
		
		
		String orgnFileName = (String) resultMap.get("fileName");												// 원본파일명
		String fullPath = FILE_SERVER_PATH + "/" + resultMap.get("uploadPath") + "/" + resultMap.get("uuid");	// 파일경로 + uuid(저장파일명)
		logger.info("fullPath :::: " + fullPath);
		
		File file = new File(fullPath);
		logger.info("file :::: " + file);
		
		mv.setViewName("downloadView");
		mv.addObject("downloadFile", file);
		mv.addObject("fileName", orgnFileName); //fileName 추가
		
		return mv;
	}
	
	
}
