package com.kh.bookmanager.book;

import java.util.List;

import oracle.net.aso.b;

public class BookController {
	
	private BookService bookService =  new BookService();

	public List<Book> searchBookByTitle(String keyword) {
		return bookService.selectBookByTitle(keyword);
	}

	public List<Book> searchBookWithRank() {
		return bookService.selectBookWithRank();
	}

	public List<Book> searchAll() {
		return bookService.findAllBook();
	}

	public int registBook(Book book) {
		return bookService.persistBook(book);
	}

	public int modifyBook(Long bkIdx, String info) {
		Book book = new Book();
		book.setBkIdx(bkIdx);
		book.setInfo(info);
		return bookService.modifyBook(book);
	}

	public int removeBook(Long bkIdx) {
		return bookService.removeBook(bkIdx);
	}
	
	
}
