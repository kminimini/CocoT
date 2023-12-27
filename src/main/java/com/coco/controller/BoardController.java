package com.coco.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coco.domain.Board;
import com.coco.domain.Member;
import com.coco.domain.Search;
import com.coco.repository.BoardRepository;
import com.coco.security.SecurityUser;
import com.coco.service.BoardService;



@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
    private BoardRepository boardRepo;
		
	@RequestMapping("/getBoardList")
	public String getBoardList(Model model, Search search, @RequestParam(defaultValue = "0") int page,
	                           @AuthenticationPrincipal SecurityUser principal) {

	    // 사용자가 로그인한 경우에만 처리
	    if (principal != null && principal.getMember() != null) {
	        Member member = principal.getMember();

	        if (search.getSearchCondition() == null) {
	            search.setSearchCondition("btitle");
	        }

	        if (search.getSearchKeyword() == null) {
	            search.setSearchKeyword("");
	        }

	        // 디버깅을 위한 로그 출력
	        System.out.println("Search in Controller: " + search);

	        Pageable pageable = PageRequest.of(page, 10, Sort.by("bseq").descending());
	        Page<Board> boardList = boardService.getBoardList(pageable, search);

	        model.addAttribute("boardList", boardList);
	        model.addAttribute("member", member);
	        model.addAttribute("search", search);

	        // Thymeleaf 템플릿의 이름을 반환합니다.
	        return "getBoardList";
	    } else {
	        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
	        return "redirect:/system/login";
	    }
	}    
	
	@GetMapping("/insertBoard")
	public String insertBoardView(Model model, HttpSession session) {
	    // Spring Security를 사용하여 로그인 상태 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof SecurityUser) {
	        // 로그인 상태일 때의 로직 처리
	        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
	        
	        // 사용자 정보를 모델에 추가
	        model.addAttribute("loggedInMember", securityUser.getMember());

	        // 여기에 추가로 처리할 로직이 있다면 추가하세요.
	        // 예: 게시판 목록 가져오기 등

	        return "insertBoard";
	    } else {
	        // 로그인 상태가 아니면 로그인 페이지로 리디렉션
	        return "redirect:/system/login";
	    }
	}

	@PostMapping("/insertBoard")
	public String insertBoard(Board board, @RequestParam(name = "isSecret", defaultValue = "false") boolean isSecret) {
	    // Spring Security를 사용하여 로그인 상태 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof SecurityUser) {
	        // 로그인 상태일 때의 로직 처리
	        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
	        Member member = securityUser.getMember();

	        // 비밀글 여부 설정
	        board.setSecret(isSecret);
	        
	        // 작성자 정보 설정
	        board.setMember(member);

	        // 게시글 등록 로직
	        boardService.insertBoard(board);

	        return "redirect:getBoardList";
	    } else {
	        // 로그인 상태가 아니면 로그인 페이지로 리디렉션
	        return "redirect:/system/login";
	    }
	}
	
	@GetMapping("/getBoard")
	public String getBoard(@RequestParam Long bseq, Model model, @AuthenticationPrincipal SecurityUser principal) {
	    // 글 정보 가져오기
	    Board board = boardService.getBoardById(bseq);

	    // Spring Security를 이용하여 현재 로그인한 사용자 정보 가져오기
	    SecurityUser securityUser = principal != null ? principal : null;

	    // 만약 글이 비밀글이라면, 그리고 현재 사용자가 작성자나 어드민이 아니라면 접근을 거부합니다.
	    if (board.isSecret() && (securityUser == null || (!securityUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")) && !securityUser.getMember().getId().equals(board.getMember().getId())))) {
	        // 비밀글이고 현재 사용자가 작성자나 어드민이 아닌 경우
	        return "accessDenied"; // 접근 거부 페이지로 리다이렉트
	    }

	    // 조회수 증가 로직
	    boardService.getBoard(board);

	    model.addAttribute("board", board);

	    return "getBoard"; // 글 조회 페이지로 이동
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(Board board, RedirectAttributes redirectAttributes) {
	    // Spring Security를 사용하여 로그인 상태 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof SecurityUser) {
	        // 로그인 상태일 때의 로직 처리
	        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
	        Member loggedInUser = securityUser.getMember();

	        // Board 정보 가져오기
	        Board existingBoard = boardService.getBoardById(board.getBseq());

	        // 작성자 확인 - 자신이 작성한 글인지 또는 관리자인지 검사
	        if (existingBoard != null && (existingBoard.getMember() != null && existingBoard.getMember().getId().equals(loggedInUser.getId()) || securityUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")))) {
	            // 작성자가 맞거나 관리자인 경우에 업데이트 진행
	            board.setMember(existingBoard.getMember());
	            boardService.updateBoard(board);
	            return "redirect:/getBoardList";
	        } else {
	            // 작성자가 아니면 예외 처리 또는 다른 로직 수행
	            return "redirect:/accessDenied";
	        }
	    } else {
	        // 로그인 상태가 아니면 로그인 페이지로 리디렉션
	        return "redirect:/system/login";
	    }
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(@RequestParam Long bseq) {
	    SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Member loggedInUser = securityUser.getMember();

	    // 해당 글의 작성자 정보 가져오기
	    Board board = boardService.getBoardById(bseq);

	    // 현재 로그인한 사용자가 관리자이거나 글의 작성자와 일치하는지 확인
	    if (securityUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")) || loggedInUser.getId().equals(board.getMember().getId())) {
	        boardService.deleteBoard(board);
	        return "redirect:/getBoardList";
	    } else {
	        // 일치하지 않을 경우 예외 처리 또는 다른 로직 수행
	        return "redirect:/accessDenied";
	    }
	}
	
	@GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied"; // accessDenied.html 뷰 파일 이름에 따라 수정
    }
	
	@GetMapping("/qna")
	public String showQnAPage() {
	    
	    return "qna"; // Thymeleaf 템플릿의 이름
	}

}
