package com.kh.bookmanager.rent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
	
	//CasecadeType
	// PERSIST : PERSIST를 수행할 때 연관엔티티도 함께 수행
	// REMOVE : 엔티티를 삭제할 때 연관 엔티티도 함께 삭제
	// MERGE : 준영속상태의 엔티티를 MERGE 할 때 연관엔티티도 함께 MERGE
	// DETACH : 영속상태의 엔티티를 준영속 상태로 만들 때 연관엔티티도 함께 수행
	// ALL : PERSIST + REMOVE + MERGE + DETACH
	
	//하나의 렌트에 여러개의 렌트북  //mappedBy : 연관관계의 주인을 지정 (주인이 아닌쪽에서 설정해줌)
	//FetchType.EAGER : Lazy Initialization 설정을 끔
	@OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RentBook> rentBooks = new ArrayList<RentBook>(); //널포인트이셉션 방지를 위해 초기화를 바로 해줌
	
	@Column(columnDefinition = "date default sysdate")
	private LocalDateTime regDate;
	
	@Column(columnDefinition = "number default 0")
	private Boolean isReturn;
	private String title;
	
	@Column(columnDefinition = "number default 0")
	private Integer rentBookCnt;
	
	public void changeRentBooks(List<RentBook> rentBooks) {
		this.rentBooks = rentBooks;
		for (RentBook rentBook : rentBooks) {
			rentBook.setRent(this);
		}
	}
	

}
