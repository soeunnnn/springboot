package com.kh.toy.member;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.common.exception.HandlableException;
import com.kh.toy.member.validator.JoinForm;
import com.kh.toy.member.validator.JoinFormValidator;

	//1. @Controller : 해당 클래스를 applicationContext에 bean으로 등록
	//					Controller와 관련된 annotation을 사용할 수 있게 해준다.
	//2. @RequestMapping : 요청 URL과 Controller의 메서드 매핑을 지원
	//					클래스 위에 선언할 경우, 해당 클래스의 모든 메서드가 지정된 경로를 상위경로로 가진다.
	//3. @GetMapping : Get 방식의 요청 URL과 Controller의 메서드 매핑을 지원
	//4. @PostMapping : Post 방식의 요청 URL과 Controller의 메서드 매핑을 지원
	//5. @RequestParam : 요청 파라미터를 컨트롤러 메서드의 매개변수에 바인드
	//					단 Content-type이 application/x-www-form-urlEncoded인 경우에만 가능
	//					FormHttpMessageConverter가 동작
	//6. @ModelAttribute : 요청 파라미터를 setter사용해 메서드 매개변수에 선언된 객체에 바인드,
	//						Model객체의 attribute로 자동으로 저장
	//7. @RequestBody : 요청 body를 읽어서 자바의 객체에 바인드
	//					application/x-www-form-urlEncoded를 지원하지 않는다.
	//8. @RequestHeader : 요청 헤더를 메서드의 매개변수에 바인드
	//9. @SessionAttribute : 원하는 session의 속성값을 매개변수에 바인드
	//10. @CookieValue : 원하는 cookie값을 매개변수에 바인드
	//11. @PathVariable : url 템플릿에 담긴 파라미터값을 매개변수에 바인드
	//12. @ResponseBody : 메서드가 반환하는 값을 응답 body에 작성
	//13. Servlet객체를 컨트롤러의 매개변수에 선언해 주입받을 수 있다.
	//	HttpServletRequest, HttpServletResponse, HttpSession

@Controller
@RequestMapping("member")  //밑에서 mapping에 url작성할 때 앞에 member 생략가능
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemberService memberService;
	private JoinFormValidator joinFormValidator;
	
	//주입받아야하는 것들만 매개변수가 있는 생성자로 만들기
	public MemberController(MemberService memberService, JoinFormValidator joinFormValidator) {
		super();
		this.memberService = memberService;
		this.joinFormValidator = joinFormValidator;
	}

	/*  Model 속성명 자동 생성 규칙
		com.myapp.Product becomes "product"
		com.myapp.MyProduct becomes "myProduct"
		com.myapp.UKProduct becomes "UKProduct"
	*/

	@GetMapping("join")  //   member/join-form.jsp로 요청을 재지정해줌
	public void joinForm(Model model) { //모델 넣어놓기
		model.addAttribute(new JoinForm());
		//throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
	}
	
	@PostMapping("join")  //join-form.jsp의 메서드가 post니까 PostMapping, 들어오는 요청이랑 돌려보내야하는 jsp위치가 다르므로 리턴타입을 String으로
	public String join(@Validated JoinForm form
			, Errors errors //errors -> 반드시 검증될 객체 바로 뒤에 작성
			, Model model
			, HttpSession session
			, RedirectAttributes redirectAttr
			) {
		
		logger.debug("post 조인메서드 세션아이디 확인 : " + session.getId());
		
		if(errors.hasErrors()) { //통과된 친구가 있는지 없는지 알려줌
			return "member/join";
		}
		
		//token 생성
		String token = UUID.randomUUID().toString();
		session.setAttribute("persistUser",  form); //회원가입때 입력한 정보 저장
		session.setAttribute("persistToken", token); //토큰 저장
		
		memberService.authenticateByEmail(form, token);
		redirectAttr.addFlashAttribute("message", "이메일이 발송되었습니다.");
		
		return "redirect:/";
	}
	
	@GetMapping("join-impl/{token}") //회원가입 진행하는 부분
	public String joinImpl(@PathVariable String token //<-이 매개변수랑 GetMapping어노테이션 안의 변수명과 같아야함(어노테이션쪽으로 넘어온 토큰값이 자동으로 스트링변수에 매핑됨)
						,@SessionAttribute(value="persistToken", required = false) String persistToken //세션에 담겨있는 persistToken값을 String persistToekn에 담기
						,@SessionAttribute(value = "persistUser", required = false) JoinForm form
						,HttpSession session
						,RedirectAttributes redirectAttrs){
		
		logger.debug(session.getId());
		logger.debug(persistToken);
		logger.debug(token);
	
		if(!token.equals(persistToken)) {
			throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		}
		
		memberService.persistMember(form);
		redirectAttrs.addFlashAttribute("message", "회원가입을 환영합니다. 로그인 해주세요.");
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		return "redirect:/member/login";
	}
	
	@PostMapping("join-json")
	public String joinwithJson(@RequestBody Member member) {
		logger.debug(member.toString());
		return "index";
	}
	
	//로그인 페이지 이동 메서드
	//메서드명 : login
	@GetMapping("login")
	public void login() {}


	
	@GetMapping("mypage")
	public String mypage(@CookieValue(name="JSESSIONID", required = false) String sessionId //원하는 쿠키값 가져와서 String에 넣어줌
					  , @SessionAttribute(name="authentication", required = false) Member member   //우리가 원하는 세션의 속성값을 받아와서 member에 넣어줌
					  , HttpServletResponse response) { //request, response는 웬만하면 쓰지말기
		
		//Cookie 생성 및 응답헤더에 추가
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName("testCookie"); //이름 붙여주고
		cookieGenerator.addCookie(response, "test_cookie");  //쿠키값 추가해줌
		
		logger.debug("@CookieValue : " + sessionId);
		logger.debug("@SessionAttribute : " + member);
		
		return "member/mypage";
	}
	
	@GetMapping("id-check") //joinForm.js에서 id-check로 넘어오는거 매핑
	@ResponseBody //리턴하는 값이 responseBody에 직접 들어가게 됨
	public String idCheck(String userId) { //요청파라미터 이름이 userId인 파라미터값을 String userId에 매핑해줌
		if(memberService.existMemberById(userId)) {
			return "disable";
		} else {
			return "available";
		}

	}
	
	
	
	
	
}
