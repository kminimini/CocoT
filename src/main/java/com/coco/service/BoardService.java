package com.coco.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.coco.config.*;
import com.coco.domain.Board;
import com.coco.domain.Search;

public interface BoardService {

	List<Board> getBoardList(Board board);
	
	public Board getBoard(Board board);

	void insertBoard(Board board);

	void updateBoard(Board board);

	void deleteBoard(Board board);

	Page<Board> getBoardList(Pageable pageable, Search search);
	
	Board getBoardById(Long bseq);
	
	Board getBoard(Long bseq);
	
}