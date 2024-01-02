package com.coco.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coco.domain.Board;
import com.coco.domain.Member;
import com.coco.domain.Notice;
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
	
	@Autowired
	private HttpSession httpSession;
	
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

            // 현재 사용자가 글의 소유자인지 확인
            boolean isOwner = checkIfUserIsOwner(existingBoard); // 사용자가 소유자인지 확인하는 메서드를 호출해야 합니다.

            // 현재 세션에서 조회한 게시물인지 확인
            String sessionKey = "viewedBoard_" + seq;
            if (!isOwner && httpSession.getAttribute(sessionKey) == null) {
                Long newCnt = existingBoard.getCnt() + 1L;
                existingBoard.setCnt(newCnt);
                boardRepo.save(existingBoard);

                // 현재 세션에 조회한 게시물로 표시
                httpSession.setAttribute(sessionKey, true);

                return existingBoard;
            } else {
                // 이미 조회한 게시물이거나 자신의 게시물인 경우 조회수 증가 없이 기존 게시물 반환
                return existingBoard;
            }
        } else {
            // 해당하는 seq에 해당하는 Board가 없을 경우 처리 (예: 예외를 던지거나 새로운 Board를 생성하는 등)
            return null;
        }
    }
	
	// 현재 사용자가 게시물의 소유자인지 확인하는 메서드
    private boolean checkIfUserIsOwner(Board board) {
        // Spring Security를 사용하는 경우, 현재 사용자의 Authentication 객체를 가져올 수 있습니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 현재 사용자의 ID를 가져오는 방법은 사용 중인 시스템에 따라 다를 수 있습니다.
        // Spring Security에서는 Principal을 통해 현재 사용자의 정보에 접근할 수 있습니다.
        String currentUser = authentication.getName(); // 현재 사용자의 ID를 가져옴

        // Board 엔티티에서 소유자 정보를 가져옵니다.
        Member owner = board.getMember();

        // Board 엔티티의 소유자와 현재 사용자의 ID를 비교하여 일치하면 true 반환
        return owner != null && owner.getId().equals(currentUser);
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

