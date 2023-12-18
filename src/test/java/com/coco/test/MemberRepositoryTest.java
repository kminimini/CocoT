package com.coco.test;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coco.domain.Member;
import com.coco.repository.MemberRepository;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;
	
	@Disabled
	@Test
	public void testGetMember() {
		Optional<Member> result = memberRepository.findByEmail("email@email.com");
		
		System.out.println(result);
	}
}
