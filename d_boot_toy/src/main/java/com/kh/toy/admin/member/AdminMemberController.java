package com.kh.toy.admin.member ;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.toy.member.Member;

@Controller
@RequestMapping("admin")
public class AdminMemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminMemberService adminMemberService;

	@GetMapping("member/member-list")
	public void searchAllMembers(Model model) {  //컨트롤러 메서드가 호출되는 시점에 스프링프레임워크가 모델객체를 만들어서 주입해줌
		List<Member> members = adminMemberService.selectAllMembers();	
		logger.debug(members.toString());
		
		model.addAttribute("members", members); //값을 어트리뷰트에 담음(어트리뷰트명은 jsp에서 사용했던 어트리뷰트명으로 넣으면됨)
		
	}
}
