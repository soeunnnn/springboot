package com.kh.toy.board ;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.toy.member.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BoardService boardService; //프록시 객체 받아옴
	
	@GetMapping("board-form")
	public void boardForm() {};

	@PostMapping("upload")
	public String uploadBoard(
				@RequestParam List<MultipartFile> files //멀티파트타입 매개변수 선언하면 알아서 요청 파싱해서 데이터 넣어줌
 				, @SessionAttribute("authentication") Member member
				, Board board
			) {
		
		logger.debug("fileSize :" + files.size());
		logger.debug("files.0 : " + files.get(0));
		logger.debug("mf.isEmpty : " + files.get(0).isEmpty());
		
		board.setMember(member);
		boardService.insertBoard(files, board);
		
		return "redirect:/"; //인덱스로 돌려보냄
	}
	
	@GetMapping("board-detail")
	public void boardDetail(Model model, String bdIdx) { //데이터 받아와서 넣어줘야하니까 model객체 필요
		Map<String,Object> commandMap = boardService.selectBoardByIdx(bdIdx);
		model.addAllAttributes(commandMap); //addAllAttributes => 맵에 담겨있는 키와 밸류를 꺼내서 어트리뷰트에 담아줌
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
