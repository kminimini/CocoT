package com.coco.test;

import java.util.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coco.domain.Board;
import com.coco.domain.Member;
import com.coco.domain.Role;
import com.coco.repository.BoardRepository;
import com.coco.repository.MemberRepository;

@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	private MemberRepository memberRepo;
	
	
	@Disabled
    @Test
    public void testManyToOneInsert() {
		 Member member1 = Member.builder() 
				 .name("김양현") 
				 .id("didgus67")		 
				 .ename("KIMYANGHYEON") 
				 .rrnumber("1998.01.08") 
				 .password("didgus5766!")		 
				 .address("서울시 강남구") 
				 .detailAddress("청담동") 
				 .email("didgus67@naver.com")		  
				 .phone("010-1234-1234") 
				 .role(Role.ROLE_MEMBER) 
				 .enabled("true") 
				 .build();
		  
		 memberRepo.save(member1);
		 
        
        Member member2 = Member.builder()
                .name("관리자")
                .id("admin")
                .ename("ADMIN")
                .rrnumber("1998.01.05")
                .password("admin1234@")
                .address("서울시 강남구 서초구")
                .detailAddress("밍밍빌딩")
                .email("admin33@email.com")
                .phone("010-1111-2222")
                .role(Role.ROLE_ADMIN)
                .enabled("true")
                .build();

        memberRepo.save(member2);
      
    }
}
