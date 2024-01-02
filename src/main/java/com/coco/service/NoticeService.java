package com.coco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coco.domain.Notice;

@Service
public interface NoticeService {


	List<Notice> getNoticeList(Notice notice);
	
	void InsertNotice(Notice notice);
	
	void getNotice(Notice notice);
	
	Notice getNoticeById(Long nseq);

}