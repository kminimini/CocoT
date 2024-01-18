package com.coco.test;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coco.domain.Notice;
import com.coco.repository.NoticeRepository;

@SpringBootTest
public class NoticeRepositoryTest {

	
	@Autowired
	private NoticeRepository noticeRepo;
	
	@Disabled
	@Test
	public void testInsertNotice() {
		
		Notice notice1 = Notice.builder()
				.ntitle("[공지사항] 개인정보 처리방침 변경안내처리방침")
				.ncontent("개인정보 처리방침 변경안내처리방침")
				.createDate(new Date())
				.build();

		noticeRepo.save(notice1);
		
		Notice notice2 = Notice.builder()
				.ntitle("[공지사항] 서버 점검 공지")
				.ncontent("서버 점검 공지")
				.createDate(new Date())
				.build();

		noticeRepo.save(notice2);
		
		Notice notice3 = Notice.builder()
				.ntitle("[공지사항] 서비스 이용에 대한 공지")
				.ncontent("서비스 이용에 대한 공지")
				.createDate(new Date())
				.build();

		noticeRepo.save(notice3);
	
}
	
}