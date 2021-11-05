package com.kh.toy.common.util.file;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.toy.common.code.Config;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class FileInfo {
   
	@Id
	@GeneratedValue
	private Long flIdx;
	
	private String originFileName;
	private String renameFileName;
	private String savePath;
	
	@Column(columnDefinition = "date default sysdate")
	private LocalDate regDate;
	
	@Column(columnDefinition = "number default 0")
	private Boolean isDel;

	public String getLink() {
		return Config.DOMAIN.DESC + "/file/" + savePath + renameFileName;
	}

	// 다운로드 시 사용할 패스 (브라우저에서 경로요청이 절대경로로 요청을 하기때문에 절대경로로 붙여줌)
	public String getDownloadPath() {
		return Config.UPLOAD_PATH.DESC + savePath;
	}
  
   
   

}