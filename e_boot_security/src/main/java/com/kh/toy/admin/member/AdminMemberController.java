package com.kh.toy.admin.member ;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.toy.member.Member;

@Controller
@RequestMapping("admin")
public class AdminMemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminMemberService adminMemberService;

	@GetMapping("member/member-list")
	public void searchAllMembers(Model model, @RequestParam(required = false, defaultValue = "1") int page) {  //컨트롤러 메서드가 호출되는 시점에 스프링프레임워크가 모델객체를 만들어서 주입해줌
		Map<String,Object> commandMap = adminMemberService.findAllMembers(page);	
		
		model.addAllAttributes(commandMap);
	}
}
