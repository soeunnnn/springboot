package com.kh.spring.springdata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringDataTest {
	
	@Autowired
	private SpringDataRepository springDataRepository;
	
	@Test
	@DisplayName("모든 도서 검색 테스트")
	public void findAllBook() {
		springDataRepository.findAll().stream().forEach(e -> System.out.println(e));
	}
	

}
