package com.coco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coco.domain.Member;
import com.coco.dto.JoinFormDto;

@Service
public interface MemberService {

	String entitySave(JoinFormDto joinFormDto);

	void save(Member member);

	Member getMember(String email);

	public List<Member> findId(String email) throws Exception;

	// 이름과 전화번호를 통한 이메일 찾기
	String findEmailByNameAndPhone(String name, String phoneNumber) throws Exception;

	// 아이디 중복체크
	int findidCheck(String id);

	// 아이디와 이메일로 멤버 찾기
	Member getMemberByIdAndEmail(String id, String email);

	Optional<Member> getMemberByEmail(String username);

	// 회원 목록 불러오기
	List<Member> getAllMembers();

	// mypage
	Member findById(Long id);

	// 회원 탈퇴
	void deleteMemberById(Long memberId);

	// 비밀번호 변경
	boolean isCurrentPasswordValid(String currentPassword);

	boolean changePassword(String currentPassword, String newPassword);

	Member getCurrentMember();

}
