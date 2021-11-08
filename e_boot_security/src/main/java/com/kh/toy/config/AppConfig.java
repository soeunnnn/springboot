package com.kh.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration 이 선언된 클래스에서 @Bean이 선언된 메서드가 반환하는 객체는 ApplicationCotext에 등록된다.
@Configuration
public class AppConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		
		SimpleClientHttpRequestFactory requestFactory = 
				new SimpleClientHttpRequestFactory();
		
		requestFactory.setReadTimeout(5000);
		requestFactory.setConnectTimeout(5000);
		
		return new RestTemplate(requestFactory);
	}
	
	@Bean
	public ObjectMapper onjectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxInMemorySize(10485760);
		multipartResolver.setMaxUploadSize(10485760);
		return multipartResolver;
		
	}

}
