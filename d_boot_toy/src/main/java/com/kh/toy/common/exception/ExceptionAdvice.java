package com.kh.toy.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//컨트롤러에서 예외가 발생했을 때 이 클래스로 다 넘어옴

@Component
@ControllerAdvice(basePackages = "com.kh.toy") //com.kh.toy 패키지 아래에 있는 모든 컨트롤러에서 발생하는 예외들을 가져옴
public class ExceptionAdvice {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//예외가 발생했음으로 응답상태코드를 500번으로 지정, default 200
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) //500번 에러로 날리는 코드임
	@ExceptionHandler(HandlableException.class) //발생한 예외가 핸들러블이셉션일 경우에만 이셉션핸들러가 작동되도록
	public String handlableExceptionProcess(HandlableException e, Model model) {
		model.addAttribute("msg", e.error.MESSAGE);
		model.addAttribute("url", e.error.URL);
		return "error/result";
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataAccessException.class) //데이타엑세스이셉션이 발생했을 경우 작동
	public String dataAccessExceptionProcess(DataAccessException e, Model model) {
		logger.error(e.getMessage());
		model.addAttribute("msg", "데이터베이스 접근 도중 예외가 발생했습니다.");
		model.addAttribute("url", "/");
		return "error/result";
	}
	
	
	
	
	
	
	
	
	
	
	
}
