package com.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.board.service.BoardService;
import com.board.vo.BoardAttachVO;
import com.board.vo.BoardVO;
import com.board.vo.CategoryVO;
import com.board.vo.PagingVO;
import com.board.vo.summernoteImgVO;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	/*
	 * 목록 조회
	 * 페이징
	 * 검색
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getListPage(Model model, PagingVO pvo) throws Exception{
		logger.info("목록 조회 페이지 접속 + 페이징");
	
		// 목록 조회 + 페이징
		List<BoardVO> list = service.listPage(pvo);
		
		model.addAttribute("list", list);
		
		// 게시물 총 개수
		int total = service.totalCount(pvo);
		
		logger.info("total :: " + total);
		
		PagingVO page = new PagingVO(pvo, total);
		
		logger.info("page :: " + page);
		
		model.addAttribute("page", page);
	}
	
	/*
	 * 글 작성 페이지 접속
	 * 카테고리
	 * CK에디터
	 * 파일 업로드
	 */
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite(Model model, HttpServletRequest request) throws Exception {
		logger.info("글 등록 페이지 접속");
		
		HttpSession session = request.getSession();
		Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("user");
		
		List<CategoryVO> category = service.category();
		logger.info("category ::" + category);
		
		model.addAttribute("userName", sessionMap.get("user_name"));
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	/*
	 * 글 작성
	 */
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(@RequestParam(value="uploadFile") MultipartFile[] uploadFile, BoardVO vo, HttpServletRequest request) throws Exception {
		logger.info("글 등록");
		
		// 작성자 user_id 세팅(세션)
		HttpSession session = request.getSession();
		Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("user"); // userController에서 만든 user라는 이름의 세션을 sessionMap 변수에 넣어줌 
		vo.setUser_id((String) sessionMap.get("user_id")); // BoardVO에 user_id를 넣어줌(글 작성 시 user_id를 넣기 위해)
		
		// 글등록
		BoardVO resultVO = service.write(vo);	// BoardMapper에서 selectKey로 board_no 채번(번호를 새로 부여)
		logger.info("boardNo :: {}", resultVO.getBoard_no());	// 채번된 게시판번호
		
		// 파일 업로드
		logger.info("파일 업로드 확인 ");
		
		String uploadFolder = "D:\\upload";	// 업로드 경로
		
		String uploadFolderPath = getFolder();
		
		// make 년-월-일 folder ----
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		logger.info("업로드 경로 :: " + uploadPath);
		
		if (uploadPath.exists() == false) {	// 경로 폴더가 없으면
			uploadPath.mkdirs();	// 폴더 생성
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {

			logger.info("---------------------------");
			logger.info("파일명 :: " + multipartFile.getOriginalFilename());
			logger.info("파일 크기 :: " + multipartFile.getSize());
			
			String orgFileName = multipartFile.getOriginalFilename();
			String uploadFileName = "";
			
			// IE has file path
			uploadFileName = orgFileName.substring(orgFileName.lastIndexOf("\\") + 1);
			logger.info("only file name :: " + uploadFileName);
			
			// 파일 이름 중복 방지를 위한 UUID 적용
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			
			// 파일 존재 여부 체크
			if(saveFile != null && orgFileName.length() != 0) {
			
				// attach 테이블에 vo값 세팅
				BoardAttachVO fileVO = new BoardAttachVO();
				fileVO.setBoard_no(resultVO.getBoard_no());	// 게시판번호
				fileVO.setFileName(orgFileName);			// 원본파일명
				fileVO.setUuid(uploadFileName);				// 변경파일명(uuid)
				fileVO.setUploadPath(uploadFolderPath);		// 파일경로
				
				logger.info("uploadFolderPath :: " + uploadFolderPath);
				
				try {
					multipartFile.transferTo(saveFile);	// 파일 업로드 시 원래 파일의 이름(+UUID)으로 경로에 저장
					
					// 파일정보 DB에 insert
					service.insertFile(fileVO);
					
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				} // end catch
			
			}
			
		} // end for
		
//		return "redirect:/board/list";
		return "redirect:/board/detail?board_no=" + vo.getBoard_no();
	}
	
	/*
	 *  summernote 이미지 업로드
	 */
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
		logger.info("=================================================================");
		JsonObject jsonObject = new JsonObject();
		 
		// String fileRoot = "D:\\upload\\summernote\\"; // 외부경로로 저장을 희망할때.
		
		// 내부경로로 저장
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		logger.info("contextRoot :::: " + contextRoot);
		
		String fileRoot = contextRoot + "/resources/fileupload/";							// 프로젝트 내부 경로
		
		String originalFileName = multipartFile.getOriginalFilename();						// 오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 파일 확장자
		String savedFileName = UUID.randomUUID() + extension;								// 저장될 파일명
		logger.info("savedFileName :::: " + savedFileName);
		
		File targetFile = new File(fileRoot + savedFileName);
		logger.info("targetFile :::: " + targetFile);
		
		// 파일 존재 여부 체크
		if(targetFile != null && originalFileName.length() != 0) {
			
			//summernoteImg 테이블에 vo값 세팅
//			summernoteImgVO imgVO = new summernoteImgVO();
//			imgVO.setBoard_no();
			
			try {
				// 지금은 올리자마자 폴더에 저장됨 -> 글 등록 버튼을 눌러야 폴더에 저장되게 하기
				// DB에 저장(안됨, 파일 업로드 부분 참고) / 내부 폴더에 저장(됨)
				
					InputStream fileStream = multipartFile.getInputStream();
					FileUtils.copyInputStreamToFile(fileStream, targetFile);					// 파일 저장
					jsonObject.addProperty("url", "/resources/fileupload/" + savedFileName); 	// contextroot + resources + 저장할 내부 폴더명
					jsonObject.addProperty("responseCode", "success");
					
			} catch (IOException e) {
				FileUtils.deleteQuietly(targetFile);	// 파일 저장 시도 중 에러가 나면 저장된 파일 삭제
				jsonObject.addProperty("responseCode", "error");
				e.printStackTrace();
			}
		
		}
		
		String a = jsonObject.toString();
		logger.info("jsonObject.toString() :::: " + jsonObject.toString());
		
		return a;
	}
	
	/*
	 * 상세 페이지
	 * 첨부파일 조회
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void getDetail(@RequestParam("board_no") int board_no, Model model, PagingVO pvo) throws Exception{
		logger.info("상세 페이지");
		
		BoardVO vo = service.detail(board_no);
		logger.info("service.detail(board_no) :::: " + service.detail(board_no));
		
		model.addAttribute("detail", vo);
		logger.info("vo :::: " + vo);
		
		model.addAttribute("pvo", pvo);
		logger.info("pvo :::: " + pvo);
		
		List<Map<String, Object>> fileList = service.selectFileDetail(board_no);
		logger.info("service.selectFileDetail(board_no) :::: " + service.selectFileDetail(board_no));
		
		
		// 첨부파일 조회
		model.addAttribute("fileList", fileList);
		logger.info("fileList :::: " + fileList);
		
	}
	
	/*
	 * 파일 다운로드
	 */
	@RequestMapping("/common/download")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		mv.setViewName("/common/download");
		
		return mv;
	}
	
	/*
	 * 수정 페이지 접속
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("board_no") int board_no, Model model, HttpServletRequest request) throws Exception{
		logger.info("수정 페이지 접속");
		
		HttpSession session = request.getSession();
		Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("user");
		
		logger.info("sessionMap :: " + sessionMap);
		logger.info("session.getAttribute(\"user\") :: " + session.getAttribute("user"));
		
		
		BoardVO vo = service.detail(board_no);
		
		List<CategoryVO> category = service.category();
		
		model.addAttribute("detail", vo);
		model.addAttribute("category", JSONArray.fromObject(category));
		
		List<Map<String, Object>> fileList = service.selectFileDetail(board_no);
		logger.info("service.selectFileDetail(board_no) :::: " + service.selectFileDetail(board_no));
		
		// 첨부파일 조회
		model.addAttribute("fileList", fileList);
		logger.info("fileList :::: " + fileList);
	}
	
	/*
	 * 수정
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(@RequestParam(value="uploadFile") MultipartFile[] uploadFile, BoardVO vo, HttpServletRequest request) throws Exception{
		logger.info("수정");
		

		HttpSession session = request.getSession(); // 로그인 할 때 만든 session을 가져옴
		Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("user"); // user라는 이름의 세션을 sessionMap 변수에 넣어줌 

		// 작성자 user_id 세팅
		vo.setUser_id((String) sessionMap.get("user_id")); // BoardVO에 user_id를 넣어줌(글 작성 시 user_id를 넣기 위해)
		
//		service.modify(vo);
		
		logger.info("vo.getBoard_no() :: " + vo.getBoard_no());
			
		BoardVO resultVO = service.modify(vo);	// BoardMapper에서 selectKey로 board_no 채번(번호를 새로 부여)
		logger.info("boardNo :: {}", resultVO.getBoard_no());	// 채번된 게시판번호
		
		// 파일 업로드
		logger.info("파일 업로드 확인");
		
		String uploadFolder = "D:\\upload";	// 업로드 경로
		
		String uploadFolderPath = getFolder();
		
		// make 년-월-일 folder ----
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		logger.info("업로드 경로 :: " + uploadPath);
		
		if (uploadPath.exists() == false) {	// 경로 폴더가 없으면
			uploadPath.mkdirs();	// 폴더 생성
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {

			logger.info("---------------------------");
			logger.info("파일명 :: " + multipartFile.getOriginalFilename());
			logger.info("파일 크기 :: " + multipartFile.getSize());
			
			String orgFileName = multipartFile.getOriginalFilename();
			String uploadFileName = "";
			
			// IE has file path
			uploadFileName = orgFileName.substring(orgFileName.lastIndexOf("\\") + 1);
			logger.info("only file name :: " + uploadFileName);
			
			// 파일 이름 중복 방지를 위한 UUID 적용
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			
			// 파일 존재 여부 체크
			if(saveFile != null && orgFileName.length() != 0) {
			
				// attach 테이블에 vo값 세팅
				BoardAttachVO fileVO = new BoardAttachVO();
				fileVO.setBoard_no(resultVO.getBoard_no());	// 게시판번호
				fileVO.setFileName(orgFileName);			// 원본파일명
				fileVO.setUuid(uploadFileName);				// 변경파일명(uuid)
				fileVO.setUploadPath(uploadFolderPath);		// 파일경로
				
				logger.info("uploadFolderPath :: " + uploadFolderPath);
				
				try {
					multipartFile.transferTo(saveFile);	// 파일 업로드 시 원래 파일의 이름(+UUID)으로 경로에 저장
					
					// 파일정보 insert
					service.insertFile(fileVO);
					
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				} // end catch
			
			}
			
		} // end for
		
		return "redirect:/board/detail?board_no=" + vo.getBoard_no();
	}
	
	/*
	 * 삭제
	 * 삭제 유무만 표시(날짜)
	 * DB 데이터는 유지
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String postDelete(@RequestParam("board_no") int board_no) throws Exception{
		logger.info("삭제");
		
		service.delete(board_no);
		
		return "redirect:/board/list";
	}
	
	
	// 날짜별 폴더 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
}
