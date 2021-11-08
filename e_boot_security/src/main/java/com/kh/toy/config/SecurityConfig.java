package com.kh.toy.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity //기본 시큐리티 설정 대신 선언된 클래스에서 지정한 설정이 동작되도록 해주는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
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
		.anyRequest().permitAll();
		
		
		//권한이 필요한 페이지들에 접근하면 로그인페이지로 이동
		http.formLogin()
		.loginProcessingUrl("/member/login")
		.usernameParameter("userId")
		.loginPage("/member/login")
		.defaultSuccessUrl("/");
	}

}
