package com.coco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coco.domain.Board;
import com.coco.domain.QBoard;
import com.coco.domain.Search;
import com.coco.repository.BoardRepository;
import com.coco.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public List<Board> getBoardList(Board board) {
		
		return (List<Board>) boardRepo.findAll();
	}
	
	@Transactional
	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}

	
	@Transactional
	@Override
	public Board getBoard(Board board) {
	    Long seq = board.getBseq();
	    Optional<Board> optionalBoard = boardRepo.findById(seq);

	    if (optionalBoard.isPresent()) {
	        Board existingBoard = optionalBoard.get();
	        Long newCnt = existingBoard.getCnt() + 1L;
	        existingBoard.setCnt(newCnt);
	        board = existingBoard;
	        boardRepo.save(board);
	        
	        return board;
	    } else {
	        // 해당하는 seq에 해당하는 Board가 없을 경우 처리 (예: 예외를 던지거나 새로운 Board를 생성하는 등)
	        return null;
	    }
	}
	
	@Override
	public void updateBoard(Board board) {
		Board newBoard = boardRepo.findById(board.getBseq()).get();
		
		newBoard.setBtitle(board.getBtitle());
		newBoard.setBcontent(board.getBcontent());
		boardRepo.save(newBoard);
	}
	
	@Override
	public void deleteBoard(Board board) {
		
		boardRepo.deleteById(board.getBseq());
	}
	
	@Override
	public Page<Board> getBoardList(Pageable pageable, Search search) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard qboard = QBoard.board;
		
		if (search.getSearchCondition().equals("btitle")) {
			builder.and(qboard.btitle.like("%" + search.getSearchKeyword() + "%"));
		} else if (search.getSearchCondition().equals("bcontent")) {
			builder.and(qboard.bcontent.like("%" + search.getSearchKeyword() + "%"));
		}
		
		return boardRepo.findAll(builder, pageable);
	}


	@Override
	public Board getBoardById(Long bseq) {
	    return boardRepo.findById(bseq).orElse(null);
	}

	// 댓글
	@Override
    public Board getBoard(Long bseq) {
        // Spring Data JPA를 사용하여 게시글을 가져오기
        return boardRepo.findById(bseq).orElse(null);
    }

}

