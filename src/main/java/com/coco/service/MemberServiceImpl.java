package com.coco.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coco.domain.Member;
import com.coco.domain.Role;
import com.coco.dto.JoinFormDto;
import com.coco.repository.MemberRepository;
import com.coco.security.SecurityUser;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

	/*
	 * RODO - 회원 등록 또는 업데이트 이 메서드는 JoinFormDto에 제공된 정보를 기반으로 새 회원을 등록하거나 기존 회원을
	 * 업데이트합니다. 이 메서드는 JoinFormDto의 데이터를 사용하여 Member 객체를 생성하고 MemberRepository를 사용하여
	 * 저장합니다.
	 */
	@Override
	@Transactional
	public String entitySave(JoinFormDto joinFormDto) {
		Member member = Member.builder()
				.id(joinFormDto.getId())
				.name(joinFormDto.getName())
				.ename(joinFormDto.getEname())
				.rrnumber(joinFormDto.getRrnumber())
				.address(joinFormDto.getAddress())
				.detailAddress(joinFormDto.getDetailAddress())
				.email(joinFormDto.getEmail())
				.name(joinFormDto.getName())
				.password(joinFormDto.getPassword())
				.phone(joinFormDto.getPhone())
				.enabled("true") // enabled 필드의 기본값 설정
				.role(Role.ROLE_MEMBER) // role 필드의 기본값 설정
				.build();

		return memberRepository.save(member).getMid().toString();
	}

	/*
	 * TODO - 회원 저장 이 메서드는 MemberRepository를 사용하여 Member 객체를 데이터베이스에 저장합니다. 이
	 * 메서드에는 @Transactional 주석이 추가되어 작업이 원자적임을 보장합니다.
	 */
	@Override
	@Transactional
	public void save(Member member) {
		memberRepository.save(member);

	}

	/*
	 * TODO - 이름으로 멤버 가져오기 이 메서드는 제공된 이름을 기준으로 데이터베이스에서 멤버 객체를 검색합니다. 아직 구현되지 않았습니다
	 */

//	@Override
//	public Member getMember(String email) {
//		
//		Optional<Member> result = memberRepository.findByEmail(email);
//		
//		if (result.isPresent()) {
//			return result.get();
//		} else {
//			return null;
//		}
//	}

	@Override
	public Member getMember(String email) {
		return memberRepository.findByEmail(email).orElse(null);
	}

	private boolean passwordMatches(String inputPassword, String storedPassword) {
		// 비밀번호 일치 여부 확인 로직을 구현하세요.
		// 예를 들어, Spring Security의 PasswordEncoder를 사용하는 경우:
		// return passwordEncoder.matches(inputPassword, storedPassword);

		// 또는 간단한 비교로직을 사용할 수 있습니다. (주의: 실제 운영에서는 보안상의 이유로 PasswordEncoder를 사용하는 것이
		// 좋습니다.)
		return inputPassword.equals(storedPassword);
	}

	@Override
	public List<Member> findId(String email) throws Exception {
		return memberRepository.findMembersByEmail(email);
	}

	@Override
	public String findEmailByNameAndPhone(String name, String phone) throws Exception {
		Member foundMember = memberRepository.findByNameAndPhone(name, phone);

		if (foundMember != null) {
			return foundMember.getEmail();
		} else {
			return null; // 해당 정보에 맞는 멤버가 없을 경우 null 반환
		}
	}

	// 아이디 중복확인
	@Override
	public int findidCheck(String id) {
		return memberRepository.findIdCheck(id);
	}

	// 회원 목록 불러오기
	@Override
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	// 아이디와 이메일로 멤버 찾기
	@Override
	public Member getMemberByIdAndEmail(String id, String email) {
		return memberRepository.getMemberByIdAndEmail(id, email);
	}

	@Override
	public Optional<Member> getMemberByEmail(String username) {
		return memberRepository.findByEmail(username);
	}

	// mypage
	@Override
	public Member findById(Long id) {
		return memberRepository.findById(id).orElse(null);
	}

	// 비밀번호 변경
	@Override
	public boolean isCurrentPasswordValid(String currentPassword) {
		Member currentMember = getCurrentMember();
		if (currentMember != null) {
			String storedPassword = currentMember.getPassword();

			return storedPassword.equals(currentPassword);
		} else {
			// 현재 멤버 정보를 찾을 수 없는 경우
			return false;
		}
	}

	@Override
	public boolean changePassword(String currentPassword, String newPassword) {
		Member currentMember = getCurrentMember();
		String userEmail = getCurrentMember().getEmail();

		if (isCurrentPasswordValid(currentPassword)) {
			currentMember.setPassword(newPassword);
			log.info("Changing password for user: {}", userEmail);
			memberRepository.save(currentMember); // 데이터베이스에 변경을 저장

			return true;
		} else {
			// 로그에 에러 메시지 출력
			log.error("현재 비밀번호가 올바르지 않습니다.");
			// 또는 다른 방식으로 에러를 처리할 수 있습니다.

			return false;
		}
	}

	@Override
	public Member getCurrentMember() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
			SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
			// Member로의 캐스팅 불필요
			return securityUser.getMember();
		}

		return null;
	}

	// 회원 탈퇴
	@Override
	@Transactional
	public void deleteMemberById(Long memberId) {
		Member member = memberRepository.findById(memberId).orElse(null);
		if (member != null) {
			try {
				memberRepository.delete(member);
				entityManager.flush(); // 플러시 수행
			} catch (Exception e) {
				// 예외 발생 시 롤백된 것인지 확인
				System.out.println("Error deleting member: " + e.getMessage());
				logger.error("Exception during member deletion", e);
				throw new RuntimeException("Error deleting member", e);
			}
		}
	}
	
	// 댓글
	@Override
	public Member getMemberByUsername(String username) {
	    log.info("getMemberByUsername 호출 - username: {}", username);
	    return memberRepository.findById(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Member not found for username: " + username));
	}
  

}
