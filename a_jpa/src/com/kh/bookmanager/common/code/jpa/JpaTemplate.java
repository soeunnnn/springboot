package com.kh.bookmanager.common.code.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaTemplate {
	
	//쓰레드 세이프 하기때문에 static으로 올려놓고 사용가능
	private static EntityManagerFactory emf;
	
	public static void init() {
		emf = Persistence.createEntityManagerFactory("a_jpa");  //프로젝트명이 들어감 
	}
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

}
