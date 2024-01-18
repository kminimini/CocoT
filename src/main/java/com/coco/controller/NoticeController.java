package com.coco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coco.domain.Board;
import com.coco.domain.Member;
import com.coco.domain.Notice;
import com.coco.security.SecurityUser;
import com.coco.service.NoticeService;

@Controller
public class NoticeController {

	 @Autowired
	    private NoticeService noticeService; // NoticeService가 필요한 경우에만 사용

	    @GetMapping("/notice")
	    public String noticeList(Model model) {
	        List<Notice> noticeList = noticeService.getNoticeList(null); // 예시: NoticeService를 통해 공지사항 데이터를 가져옴
	        model.addAttribute("noticeList", noticeList);
	        return "/notice";
}
	    @GetMapping("/getNotice")
		public String getNotice(@RequestParam Long nseq, Model model) {
		    // 글 정보 가져오기
		    Notice notice = noticeService.getNoticeById(nseq);

		    model.addAttribute("notice", notice);

		    return "getNotice"; // 글 조회 페이지로 이동
		}
	    
	    }
