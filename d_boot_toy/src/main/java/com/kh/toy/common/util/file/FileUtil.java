package com.kh.toy.common.util.file;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.kh.toy.common.code.Config;
import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.common.exception.HandlableException;

public class FileUtil {
	
	public FileInfo fileUpload(MultipartFile file) {
		FileInfo fileInfo = null;
		
		try {
			String uploadPath = createUploadPath(createSubPath());
			fileInfo = createFileInfo(file);
			File dest = new File(uploadPath + fileInfo.getRenameFileName());
			file.transferTo(dest);
		} catch (IllegalStateException | IOException e) {
			throw new HandlableException(ErrorCode.FAILED_FILE_UPLOAD_ERROR);
		}
		
		
		return fileInfo;
	}
	
	private String createSubPath() {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DAY_OF_MONTH);

		return year + "/" + month + "/" + date + "/";
	}

	private String createUploadPath(String subPath) {
		String uploadPath = Config.UPLOAD_PATH.DESC + subPath;

		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return uploadPath;
	}

	private FileInfo createFileInfo(MultipartFile filePart) {
		String originFileName = filePart.getOriginalFilename();
		String renameFileName = UUID.randomUUID().toString(); 
		
		//확장자 붙여넣기 위한 코드
		if(originFileName.contains(".")) {
			renameFileName = renameFileName + originFileName.substring(originFileName.lastIndexOf("."));
		}
		
		String savePath = createSubPath();

		FileInfo fileInfo = new FileInfo();
		fileInfo.setOriginFileName(originFileName);
		fileInfo.setRenameFileName(renameFileName);
		fileInfo.setSavePath(savePath);

		return fileInfo;
	}


}
