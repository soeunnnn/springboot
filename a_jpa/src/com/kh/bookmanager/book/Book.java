package com.kh.bookmanager.book;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.bookmanager.member.Member;

import lombok.Data;

@Entity //entity클래스로 만들겠다는 뜻
@DynamicUpdate //변경이 감지된 속성만 쿼리에 반영
@DynamicInsert //값이 null이 아닌 속성만 쿼리에 반영
@Data
public class Book {
	
	@Id //주 키로 사용할 객체에 붙임
	@GeneratedValue //JPA 정책에 따라 식별자를 자동으로 생성(시퀀스 대신 만들어서 넣어줌)
	private Long bkIdx;
	private String isbn;
	private String category;
	private String title;
	private String author;
	private String info;
	
	//테이블 생성 시 넣을 제약조건
	@Column(columnDefinition = "number default 1")  //타입은 number, dafault 1로 설정
	private Integer bookAmt;
	
	@Column(columnDefinition = "date default sysdate")
	private Date regDate;
	
	@Column(columnDefinition = "number default 0")
	private Integer rentCnt;
	

	
	
	
	
	
	
	
	
	
	
	
	
}
