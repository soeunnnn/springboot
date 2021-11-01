package com.kh.bookmanager.book;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kh.bookmanager.common.code.jpa.JpaTemplate;

public class BookService {
	
	private BookRepository bookRepository = new BookRepository();

	public List<Book> findBooksByTitle(String keyword) {
		EntityManager em = JpaTemplate.createEntityManager();
		List<Book> books = new ArrayList<Book>();
		
		try {
			books = bookRepository.findBooksByTitle(em, keyword);
		} finally {
			em.close();
		}
		return books;
	}

	public List<Book> findBooksWithRank() {
		EntityManager em = JpaTemplate.createEntityManager();
		List<Book> books = new ArrayList<Book>();
	
		try {
			books = bookRepository.findBooksWithRank(em);
		} finally {
			em.close();
		}
		return books;
	}

	public List<Book> findAllBook() {
		EntityManager em = JpaTemplate.createEntityManager();
		List<Book> books = new ArrayList<>();
		
		try {
			books = bookRepository.findAllBook(em);
		} finally {
			em.close();
		}
		return books;
	}

	public boolean persistBook(Book book) {
		EntityManager em = JpaTemplate.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			em.persist(book);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}	
		return false;
	}

	public boolean modifyBook(Long bkIdx, String info) {
		EntityManager em = JpaTemplate.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Book book = em.find(Book.class, bkIdx);
			book.setInfo(info);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return false;
	}

	public boolean removeBook(Long bkIdx) {
		EntityManager em = JpaTemplate.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			Book book = em.find(Book.class, bkIdx);
			em.remove(book);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
