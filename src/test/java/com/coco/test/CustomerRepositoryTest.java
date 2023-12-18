//package com.popo.test;
//
//import java.util.Date;
//import java.util.stream.IntStream;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.popo.domain.Board;
//import com.popo.domain.Member;
//import com.popo.domain.Role;
//import com.popo.repository.BoardRepository;
//import com.popo.repository.MemberRepository;
//
//@SpringBootTest
//public class CustomerRepositoryTest {
//
//	@Autowired
//	private MemberRepository memberRepo;
//	
//	@Autowired
//	private BoardRepository boardRepo;
//	
//	@Disabled
//    @Test
//    public void testManyToOneInsert() {
//        Member member1 = Member.builder()
//                .name("홍길동")
//                .id("popo")
//                .password("11111111")
//                .address("서울시 관악구 신림동")
//                .detaileAddress("문성로")
//                .email("email@email.com")
//                .phone("010-0000-1111")
//                .role(Role.ROLE_MEMBER)
//                .enabled("true")
//                .build();
//
//        memberRepo.save(member1);
//        
//        Member member2 = Member.builder()
//                .name("관리자")
//                .id("admin")
//                .password("22222222")
//                .address("서울시 강남구 서초구")
//                .detaileAddress("밍밍빌딩")
//                .email("admin@email.com")
//                .phone("010-1111-2222")
//                .role(Role.ROLE_ADMIN)
//                .enabled("true")
//                .build();
//
//        memberRepo.save(member2);
//
//        IntStream.rangeClosed(1, 13).forEach(i -> {
//            Board board = Board.builder()      		
//                    .member(member1)
//                    .btitle("홍길동이 등록한 게시글 제목" + i)
//                    .bcontent("홍길동이 등록한 게시글 내용" + i)
//                    .createDate(new Date())
//                    .cnt(0L)
//                    .build();
//
//            boardRepo.save(board);
//        });
//        
//        IntStream.rangeClosed(1, 13).forEach(i -> {
//            Board board = Board.builder()          		
//                    .member(member2)
//                    .btitle("관리자가 등록한 게시글 제목" + i)
//                    .bcontent("관리자가 등록한 게시글 내용" + i)
//                    .createDate(new Date())
//                    .cnt(0L)
//                    .build();
//
//            boardRepo.save(board);
//        });
//    }
//}


