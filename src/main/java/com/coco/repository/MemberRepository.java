package com.coco.repository;

import java.util.List;
import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.coco.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	// 추가된 메서드
	Optional<Member> findById(String memberId);

	// 이메일로 멤버 찾기
	@Query("SELECT m FROM Member m WHERE m.email = :email")
	List<Member> findMembersByEmail(@Param("email") String email) throws Exception;

	// 이름과 전화번호로 멤버 찾기
	Member findByNameAndPhone(String name, String phone);

	// 아이디 중복확인
	@Query("SELECT COUNT(m.email) FROM Member m WHERE m.email = :email")
	int findEmailCheck(@Param("email") String email);

	// 아이디와 이메일로 멤버 찾기(비번)
	@Query("SELECT m FROM Member m WHERE m.id = :id AND m.email = :email")
	Member getMemberByIdAndEmail(@Param("id") String id, @Param("email") String email);

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.password = :newPassword WHERE m.id = :id")
	void updateUserPassword(@Param("id") String id, @Param("newPassword") String newPassword);

	Member getMemberByEmail(String email);

	boolean existsById(String id);

	// 댓글
	Optional<Member> findByUsername(String username);

	// 회원탈퇴 암호화
	String findPasswordById(Long id);

}
