package com.coco.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coco.domain.Qna;
import com.coco.repository.QnaRepository;

@SpringBootTest
public class QnaRepositoryTest {

	
	@Autowired
	private QnaRepository qnaRepo;
	
	@Disabled
	@Test
	public void testInsertQna() {
		
		Qna qna1 = Qna.builder()
				.qtitle("승차권을 구입할 수 있는 방법이 궁금해요")
				.build();

		qnaRepo.save(qna1);
		
		Qna qna2 = Qna.builder()
				.qtitle("승차권은 언제부터 구매할 수 있나요?")
				.build();

		qnaRepo.save(qna2);
		
		Qna qna3 = Qna.builder()
				.qtitle("어린이나 유아는 할인 받을 수 있나요?")
				.build();

		qnaRepo.save(qna3);
	
}
	
}