package com.kh.spring.rent;

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

import com.kh.spring.member.Member;

import lombok.Data;

@Entity
@DynamicUpdate //변경이 감지된 속성만 쿼리에 반영
@DynamicInsert //값이 null이 아닌 속성만 쿼리에 반영
@Data
public class Rent {
	
	@Id
	@GeneratedValue
	private Long rmIdx;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private Member member;
	
	//CasecadeType
	// PERSIST : PERSIST를 수행할 때 연관엔티티도 함께 수행
	// REMOVE : 엔티티를 삭제할 때 연관 엔티티도 함께 삭제
	// MERGE : 준영속상태의 엔티티를 MERGE 할 때 연관엔티티도 함께 MERGE
	// DETACH : 영속사태의 엔티티를 준영속 상태로 만들 때 연관엔티티도 함께 수행
	// ALL : PERSIST + REMOVE + MERGE + DEATACH
	
	//FetchType.EAGER : Lazy Initialization 설정을 끔
	@OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RentBook> rentBooks = new ArrayList<RentBook>();
	
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
