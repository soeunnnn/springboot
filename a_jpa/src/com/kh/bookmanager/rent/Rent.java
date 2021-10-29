package com.kh.bookmanager.rent;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.bookmanager.book.Book;
import com.kh.bookmanager.member.Member;

import lombok.Data;

@Entity
@DynamicUpdate //변경이 감지된 속성만 쿼리에 반영
@DynamicInsert //값이 null이 아닌 속성만 쿼리에 반영
@Data
public class Rent {
	
	@Id
	@GeneratedValue
	private Long rmIdx;
	
	@ManyToOne //n:1 관계(현재 entity : 반대쪽 -> rent=Many / member=>one) 하나의 멤버에 여러개의 렌트가 있는거니까
	@JoinColumn(name = "userId")  //조인하고 싶은 컬럼 설정
	private Member member; //private String userId 대신 Member를 직접 들어가도록
	
	//하나의 렌트에 여러개의 렌트북
	@OneToMany(mappedBy = "rent") //mappedBy : 연관관계의 주인을 지정 (주인이 아닌쪽에서 설정해줌)
	private List<RentBook> rentBooks;
	
	@Column(columnDefinition = "date default sysdate")
	private Date regDate;
	
	@Column(columnDefinition = "number default 0")
	private Boolean isReturn;
	private String title;
	
	@Column(columnDefinition = "number default 0")
	private Integer rentBookCnt;
	
	
	

}
