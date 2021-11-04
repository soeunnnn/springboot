package com.kh.toy.member;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.toy.common.util.file.FileInfo;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Member {

	@Id
	private String userId;
	private String password;
	private String email;
	private String grade;
	private String tell;

	@Column(columnDefinition = "date default sysdate")
	private LocalDate rentableDate;

	@Column(columnDefinition = "date default sysdate")
	private LocalDate regDate;

	private Boolean isLeave;

//
}