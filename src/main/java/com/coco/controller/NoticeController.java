package com.coco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coco.domain.Notice;
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
}