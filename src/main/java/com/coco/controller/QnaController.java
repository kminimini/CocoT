package com.coco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coco.domain.Qna;
import com.coco.service.QnaService;

@Controller
public class QnaController {

	 @Autowired
	    private QnaService qnaService; // NoticeService가 필요한 경우에만 사용

	  @GetMapping("/qnaList")
	    public String qnaList(Model model) {
	        List<Qna> qnaList = qnaService.getQnaList(null);
	        model.addAttribute("qnaList", qnaList); // 수정: "qnaList"로 변경
	        return "qnaList"; // 수정: "/qnaList"에서 "qnaList"로 변경
}
}