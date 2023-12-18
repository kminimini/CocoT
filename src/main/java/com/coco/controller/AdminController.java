package com.coco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coco.domain.Member;
import com.coco.repository.MemberRepository;
import com.coco.service.BoardService;
import com.coco.service.MemberService;

@Controller
public class AdminController {

    @Autowired
    private BoardService boardService;
    
    @Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;

    // 관리자 페이지
    @GetMapping("/admin/adminPage")
    public void adminPage() {

    }
    
    @GetMapping("/index.html")
    public String showIndexPage() {
        return "index"; // 이 부분은 Thymeleaf 템플릿의 파일명과 일치해야 합니다.
    }
    
    // 회원 정보
  	@GetMapping("/admin/members")
  	public String listMembers(Model model) {
  	    List<Member> members = memberService.getAllMembers();
  	    model.addAttribute("members", members);
  	    return "/admin/members";
  	}
    
}