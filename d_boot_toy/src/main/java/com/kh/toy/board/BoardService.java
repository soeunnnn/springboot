package com.kh.toy.board;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.toy.common.util.file.FileInfo;
import com.kh.toy.common.util.file.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService{ 

	private final BoardRepository boardRepository;
	
	public void insertBoard(List<MultipartFile> multiparts, Board board) {
		
	}

	
	public Map<String, Object> selectBoardByIdx(String bdIdx) {
		return null;
	}
	
}
