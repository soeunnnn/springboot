package com.kh.toy.common.mail.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MailHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("mail")
	public String writeMailTemplate(@RequestParam Map<String,String> template, Model model) { //컨트롤러의 매개변수명과 요청파라미터명이 다를 경우에는 requestParam어노테이션 붙여야함
		logger.debug(template.toString());
		model.addAllAttributes(template);
		return "mail-template/" + template.get("mailTemplate");
	}

}
