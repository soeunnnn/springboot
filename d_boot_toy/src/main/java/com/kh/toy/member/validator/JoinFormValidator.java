package com.kh.toy.member.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.toy.member.MemberRepository;

@Component //스프링 빈으로 등록
public class JoinFormValidator implements Validator{
	
	private final MemberRepository memberRepository;
	
	//생성자로 주입받기
	public JoinFormValidator(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) { //서포트에서 지정한 클래스인 경우에만 밸리데이터 검증하도록
		return JoinForm.class.equals(clazz);  //JoinForm타입일 때만 밸리데이터 호출
	}

	@Override
	public void validate(Object target, Errors errors) { //target으로 멤버컨트롤러에서 선언해놓은 매개변수가 넘어옴
		JoinForm form = (JoinForm) target; //요청파라미터로 넘어온 값들이 담김
		
		//1. 아이디 존재 유무
		
		
		//2. 비밀번호가 8글자 이상, 숫자 영문자 특수문자 조합인지 확인
		boolean valid = Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", form.getPassword());
		if (!valid) {
			errors.rejectValue("password", "error-password", "비밀번호는 8글자 이상의 숫자 영문자 특수문자 조합입니다.");
		}
		
		//3. 휴대폰 존재 유무
		valid = Pattern.matches("^\\d{9,11}$", form.getTell());
		if(!valid) {
			errors.rejectValue("tell", "error-tell", "전화번호는 9~11자리의 숫자입니다.");
		}
		
	}

}
