package com.coco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coco.domain.Member;
import com.coco.dto.JoinFormDto;
import com.coco.repository.MemberRepository;

@Service
public interface MemberService {

	String entitySave(JoinFormDto joinFormDto);

	void save(Member member);

	Member getMember(String email);

	public List<Member> findId(String email) throws Exception;

	// 이름과 전화번호를 통한 이메일 찾기
	String findEmailByNameAndPhone(String name, String phoneNumber) throws Exception;

	// 아이디 중복체크
	int findEmailCheck(String email);

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
	
	//댓글
	public Member getMemberByUsername(String username);
	
	// 카카오 로그인(회원가입 여부 확인)
	public boolean isMemberExists(String memberId);

	boolean passwordMatches(String currentPassword, String userCurrentPassword);

	String encodePassword(String newPassword);

	String getMemberPasswordById(Long mid);

	// 회원탈퇴 비밀번호 비교 
	String getPasswordById(Long mid);


}
