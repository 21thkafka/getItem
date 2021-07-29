package org.zerock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

// Advice : AOP에서 공통 기능을 처리하는 모듈(클래스)
// 모든 예외를 처리하는 Advice 클래스
@ControllerAdvice	// Controller에서 발생하는 예외를 처리하는 Advice 클래스(AOP)
@Log4j
public class CommonExceptionAdvice {
	@ExceptionHandler(Exception.class)		// 처리할 예외를 매개변수에 지정 : Exception 클래스를 지정
	public String exception(Exception ex, Model model) {	// 예외처리를 한다.-> 브라우저로 예외를 보여줌
		log.error("Exception..." + ex.getMessage());
		model.addAttribute("exception", ex);	// 전달할 데이터
		log.error(model);
		return "error_page";	// 보여줄 view page 이름을 반환(jsp 파일)
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		return "custom404";	// 404 Not Found를 보여줄 jsp page 이름
	}
}
