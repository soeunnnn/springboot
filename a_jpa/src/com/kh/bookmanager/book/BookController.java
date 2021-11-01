package com.kh.bookmanager.book;

import java.util.List;

public class BookController {
	
	private BookService bookService =  new BookService();

	public List<Book> searchBookByTitle(String keyword) {
		return bookService.findBooksByTitle(keyword);
	}

	public List<Book> searchBookWithRank() {
		return bookService.findBooksWithRank();
	}

	public List<Book> searchAll() {
		return bookService.findAllBook();
	}

	public boolean registBook(Book book) {
		return bookService.persistBook(book);
	}

	public boolean modifyBook(Long bkIdx, String info) {
		return bookService.modifyBook(bkIdx, info);
	}

	public boolean removeBook(Long bkIdx) {
		return bookService.removeBook(bkIdx);
	}
	
	
}
