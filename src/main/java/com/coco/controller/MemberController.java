package com.coco.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.coco.domain.Notice;
//import com.coco.domain.kakaoProfile;
import com.coco.dto.JoinFormDto;
import com.coco.repository.MemberRepository;
import com.coco.service.MemberService;
import com.coco.service.NoticeService;

@Controller
public class MemberController {

	
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private NoticeService noticeService;

	private JoinFormDto joinFormDto;

	@GetMapping("/index")
	public String mainView(Model model, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 로그 추가
		System.out.println("Authentication: " + authentication);
		
		System.out.println("mainView() : access_token = " +
					(String)session.getAttribute("access_token"));

		if (authentication != null && authentication.isAuthenticated()) {
			// 사용자가 인증되었을 때, 사용자 이름 또는 관련된 사용자 정보를 제공
			model.addAttribute("authenticated", true);
			model.addAttribute("username", authentication.getName());
		}

		List<Notice> noticeList = noticeService.getNoticeList(null); // 예시: NoticeService를 통해 공지사항 데이터를 가져옴
		model.addAttribute("noticeList", noticeList);
		// 다른 로직을 추가할 수 있음

		return "index";
	}

	@GetMapping("/agree")
	public String agree() {
		return "/system/agree";
	}

	@PostMapping("/agree")
	public String checkagree(Model model) {
		return "/system/join";
	}

	@GetMapping("/findIdView")
	public String findIdView() throws Exception {
		return "/system/findIdView";
	}

	@PostMapping("/findIdView")
	public ResponseEntity<Map<String, Object>> findIdView(@RequestBody Map<String, String> requestData) {
		String name = requestData.get("name");
		String phone = requestData.get("phone");

		try {
			String foundEmail = memberService.findEmailByNameAndPhone(name, phone);

			if (foundEmail != null) {
				Map<String, Object> responseData = new HashMap<>();
				responseData.put("success", true);
				responseData.put("email", foundEmail);
				return ResponseEntity.ok(responseData);
			} else {
				Map<String, Object> responseData = new HashMap<>();
				responseData.put("success", false);
				return ResponseEntity.ok(responseData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
