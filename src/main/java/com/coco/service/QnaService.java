package com.coco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coco.domain.Qna;

@Service
public interface QnaService {


	List<Qna> getQnaList(Qna qna);
	
	void InsertQna(Qna qna);

}