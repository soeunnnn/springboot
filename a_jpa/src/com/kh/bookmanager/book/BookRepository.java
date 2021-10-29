package com.kh.bookmanager.book;

import java.util.List;

import javax.persistence.EntityManager;

public class BookRepository {

	public List<Book> findAllBook(EntityManager em){
		return em.createQuery("from Book", Book.class).getResultList();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
