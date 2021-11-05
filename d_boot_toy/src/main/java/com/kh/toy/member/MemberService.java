package com.kh.toy.member;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.toy.common.code.Config;
import com.kh.toy.common.mail.EmailSender;
import com.kh.toy.member.validator.JoinForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService { //프록시패턴으로 사용하기 위해 인터페이스 구현?

	private final MemberRepository memberRepository;
	private final EmailSender mailSender;
	private final RestTemplate http;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void persistMember(JoinForm form) {
		Member member = form.convertToMember();
		member.setGrade("일반");
		member.setPassword(passwordEncoder.encode(form.getPassword()));
		memberRepository.save(member);
	}

	public Member authenticateUser(Member member) {
		Member memberEntity = memberRepository.findById(member.getUserId()).orElse(null);
		
		if(memberEntity == null) return null;
		if(passwordEncoder.matches(member.getPassword(), memberEntity.getPassword())) {
			return memberEntity; //비번 같으면 memberEntity반환
		}
		
		return null;
	}

	public boolean existMemberById(String userId) {
		return memberRepository.existsById(userId); 
	}

	public void authenticateByEmail(JoinForm form, String token) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mailTemplate", "join-auth-mail");
		body.add("userId", form.getUserId());
		body.add("persistToken", token);
		
		//http통신을 위해 RequestEntity 사용
		//RestTemplate의 기본 Content-type : applicaion/json
		RequestEntity<MultiValueMap<String, String>> request = 
				RequestEntity.post(Config.DOMAIN.DESC + "/mail")
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body);
	
		String htmlTxt = http.exchange(request, String.class).getBody();
		mailSender.send(form.getEmail(), "회원가입을 축하합니다.", htmlTxt);
	}
	
	
}
