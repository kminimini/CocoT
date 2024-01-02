package com.coco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coco.domain.Notice;
import com.coco.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {


	@Autowired
	private NoticeRepository noticeRepository;
	
	
	// 공지사항 목록 가져오기
	 @Override
	    public List<Notice> getNoticeList(Notice notice) {
	        return noticeRepository.findAll();
	    }
	 
	 @Override
		public void InsertNotice(Notice notice) {
		 noticeRepository.save(notice);
		}
}
