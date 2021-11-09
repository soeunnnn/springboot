package com.kh.toy.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.kh.toy.common.security.OAuthFailureHandler;
import com.kh.toy.common.security.OAuthSuccessHandler;
import com.kh.toy.member.MemberService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity //기본 시큐리티 설정 대신 선언된 클래스에서 지정한 설정이 동작되도록 해주는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final DataSource dataSource;
	private final MemberService memberService;
	private final OAuthSuccessHandler oAuthSuccessHandler;
	private final OAuthFailureHandler oAuthFailureHandler;
	
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().mvcMatchers("/static/**")
		.mvcMatchers("/static/**")
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
		.mvcMatchers(HttpMethod.GET, "/board/board-form","/board/board-modify")
		.authenticated() //위에 지정한 페이지들은 인증된 사용자만 접근 허가
		.anyRequest().permitAll() //나머지들은 다 허가
		.and()
		.oauth2Login();
		
		
		//권한이 필요한 페이지들에 접근하면 로그인페이지로 이동
		http.formLogin()
		.loginProcessingUrl("/member/login")
		.usernameParameter("userId")
		.loginPage("/member/login")
		.defaultSuccessUrl("/");
		
		//로그아웃 기능
		http.logout()
		.logoutUrl("/member/logout")   //로그아웃은 포스트여야 함(index에서 포스트로 바꿔놨음)
		.logoutSuccessUrl("/member/login"); //성공하면 보내는 부분
		
		http.csrf().ignoringAntMatchers("/mail"); //원하는 요청에 대해서만 csrf 검증 해제
		
		http.oauth2Login()
		.loginPage("/member/login")
		.permitAll()
		.failureHandler(oAuthFailureHandler)
		.successHandler(oAuthSuccessHandler);
		
		http.rememberMe()
		.userDetailsService(memberService)
		.tokenRepository(tokenRepository());
		
		
	}

}
