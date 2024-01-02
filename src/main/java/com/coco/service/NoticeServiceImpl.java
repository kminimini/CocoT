package com.coco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coco.domain.Board;
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
	 

	 @Transactional
	    @Override
	    public void getNotice(Notice notice) {
	        Long seq = notice.getNseq();
	        Optional<Notice> optionalNotice = noticeRepository.findById(seq);

	    }
	 
	 @Override
		public Notice getNoticeById(Long nseq) {
		    return noticeRepository.findById(nseq).orElse(null);
		}
}
