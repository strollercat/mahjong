package com.nbcb.web.exceptionhandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class DefaultExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handle, Exception exception) {
		// TODO Auto-generated method stub
		logger.error("### united exception handler error ", exception);
		ModelAndView mv = new ModelAndView();
		/* 使用response返回 */
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			String message = null;
			if (exception != null) {
				message = exception.getMessage();
			}
			if (message != null && message.length() > 100) {
				message = message.substring(0, 100);
			}
			message = "systemerror";
			response.getWriter().write(
					"{\"errCode\":\"999999\",\"errMsg\":\"" + message + "\"}");
		} catch (IOException e) {
			logger.error("与客户端通讯异常:" + e.getMessage(), e);
		}
		return mv;
	}

}
