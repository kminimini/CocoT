package com.coco.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coco.domain.Member;
import com.coco.repository.MemberRepository;


/*
 *  MemberRepository로 회원정보를 조회하여
 *  UserDetails 객체(Spring Security에서 사용하기 위함)로 변환
 */

@Service
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepo;
	
	/* TODO 입력 파라미터 : username -> Member클래스의 id값 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// (1) MemberRepository에서 회원정보 조회
		Optional<Member> result = memberRepo.findByEmail(username);
		
		
		if (result.isPresent()) {
			Member member = result.get();
			
			// (2) UserDetails 타입 객체로 변환
			return new SecurityUser(member);
		} else {
			throw new UsernameNotFoundException(username + " : 사용자가 존재하지 않습니다.");
		}
		
	}

}
