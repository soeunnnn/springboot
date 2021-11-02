package com.kh.spring.book;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
	
	private final BookRepository bookRepository;
	
	@Transactional
	public void deleteByTitle(String title) {
		bookRepository.deleteByTitle(title);
	}
}
