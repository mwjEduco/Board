package com.board.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{

	@Override
	public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileName = (String) model.get("fileName");	// 원본파일명
		
		File file = (File) model.get("downloadFile");
		logger.info("file :: " + file);
		
		if (file != null) {
			//String fileName = null;
            String userAgent = request.getHeader("User-Agent");
            logger.info("userAgent :: " + userAgent);
            
            if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
            	 fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
            	 
            	 logger.info("fileName :: " + fileName);
            	 
			} else if (userAgent.indexOf("Chrome") > -1) {
				StringBuffer sb = new StringBuffer();
            	for(int i=0; i<fileName.length(); i++) {
            		char c = fileName.charAt(i);
            		if(c > '~') {
            			sb.append(URLEncoder.encode(""+c, "UTF-8"));
            			logger.info("sb :: " + sb);
            		}else {
            			sb.append(c);
            		}
            	}
            	fileName = sb.toString();
            	logger.info("fileName :: " + fileName);
            	
			} else {
				fileName = new String(fileName.getBytes("utf-8"));
				logger.info("fileName :: " + fileName);
			}
            
            response.setContentType(getContentType());
            response.setContentLength((int)file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            
            OutputStream outputStream = response.getOutputStream();
            FileInputStream fileInputStream = null;
            
            try {
            	fileInputStream = new FileInputStream(file);
            	FileCopyUtils.copy(fileInputStream, outputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					outputStream.flush();
				}
			}
            
            
            
		}
		
	}

}
