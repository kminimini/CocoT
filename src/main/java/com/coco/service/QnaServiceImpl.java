package com.coco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coco.domain.Qna;
import com.coco.repository.QnaRepository;

@Service
public class QnaServiceImpl implements QnaService {


	@Autowired
	private QnaRepository qnaRepository;
	
	
	// 공지사항 목록 가져오기
	 @Override
	    public List<Qna> getQnaList(Qna qna) {
	        return qnaRepository.findAll();
	    }
	 
	 @Override
		public void InsertQna(Qna qna) {
		 qnaRepository.save(qna);
		}
}
