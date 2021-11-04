package com.kh.toy.board;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.toy.common.util.file.FileInfo;

public interface BoardRepository extends JpaRepository<Board, Long>{

	
	
}
