package com.kh.bookmanager.book;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kh.bookmanager.common.code.jpa.JpaTemplate;

public class BookService {
	
	private BookRepository bookRepository = new BookRepository();

	public List<Book> selectBookByTitle(String keyword) {
		EntityManager em = JpaTemplate.createEntityManager();
		List<Book> books = new ArrayList<Book>();
		
		try {
			books = em.createQuery("from Book where title LIKE '%'||?||'%'", Book.class).setParameter(1, keyword).getResultList();
		} finally {
			em.close();
		}
	
		return books;
	}

	public List<Book> selectBookWithRank() {
		EntityManager em = JpaTemplate.createEntityManager();
		List<Book> books = new ArrayList<Book>();
		//String query = "select rownum, v.* from(select * from book where title like '%'||?||'%' order by rent_cnt desc)v where rownum < 6";
		try {
			books = em.createQuery("select rownum, v.* from(select * from Book order by RENTCNT desc)v where rownum < 6", Book.class).getResultList();
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

	public int persistBook(Book book) {
		EntityManager em = JpaTemplate.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int res = 0;
		
		try {
			em.persist(book);
			tx.commit();
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		return res;
	}

	public int modifyBook(Book book) {
		EntityManager em = JpaTemplate.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int res = 0;
		
		try {
			em.find(Book.class, book.getBkIdx())
				.setInfo(book.getInfo());
			tx.commit();
			res = 1;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return res;
	}

	public int removeBook(Long bkIdx) {
		EntityManager em = JpaTemplate.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int res = 0;
		
		try {
			Book book = em.find(Book.class, bkIdx);
			em.remove(book);
			tx.commit();
			res = 1;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
