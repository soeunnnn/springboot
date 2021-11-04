package com.kh.toy.board;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.kh.toy.common.util.file.FileInfo;
import com.kh.toy.member.Member;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Board {
	
   @Id
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   @GeneratedValue(generator = "uuid")
   private String bdIdx;
   
   @ManyToOne
   @JoinColumn(name="userId")
   private Member member;
   
   @Column(columnDefinition = "date default sysdate")
   private LocalDate regDate;
   
   private String title;
   private String content;
   
   @Column(columnDefinition = "number default 0")
   private Boolean isDel;
   
   @OneToMany(cascade = CascadeType.ALL)
   private List<FileInfo> files = new ArrayList<FileInfo>();
  
   
   

}