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

public interface MemberRepository extends JpaRepository<Member, Long> {	// String 이 원래임

	//Optional<Member> findById(String username);
	
	Optional<Member> findByEmail(String email);
	
//	public int findIdCheck(String email)throws Exception;
	
    // 추가된 메서드
    Optional<Member> findById(String memberId);
 
    // 이메일이 중복되는지 확인
//  @Query("SELECT COUNT(m.mid) FROM Member m WHERE m.email = :email")
//  int findEmailCheck(@Param("email") String email);

  // 이메일로 멤버 찾기
  @Query("SELECT m FROM Member m WHERE m.email = :email")
  List<Member> findMembersByEmail(@Param("email") String email) throws Exception;

  // 이름과 전화번호로 멤버 찾기
  Member findByNameAndPhone(String name, String phone);

  // 아이디 중복확인
   @Query("SELECT COUNT(m.mid) FROM Member m WHERE m.id = :id")
      int findIdCheck(@Param("id") String id);
  

  // 아이디와 이메일로 멤버 찾기(비번)
   @Query("SELECT m FROM Member m WHERE m.id = :id AND m.email = :email")
   Member getMemberByIdAndEmail(@Param("id") String id, @Param("email") String email);
   
   @Modifying
      @Transactional
      @Query("UPDATE Member m SET m.password = :newPassword WHERE m.id = :id")
      void updateUserPassword(@Param("id") String id, @Param("newPassword") String newPassword);

  Member getMemberByEmail(String email);
  

	
}
