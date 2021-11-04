package com.kh.toy.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ValidateResult {

	private Map<String,Object> error; //에러 담을 맵 생성
	
	public ValidateResult() {
		error = new HashMap<String, Object>();
	}
	
	public void addErrors(Errors errors) {
		//통과못한 필드값들을 맵error에 옮겨담기  //fieldError.getField() 여기에 통과못한 객체가 들어있음
		for (FieldError fieldError : errors.getFieldErrors()) {
			error.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}

	public Map<String, Object> getError() {
		return error;
	}
	
	
	
	
}
