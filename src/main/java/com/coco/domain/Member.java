package com.coco.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.popo.dto.JoinFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 고객 En
 */

@Entity
@Getter
@Setter
@ToString(exclude = {"boardList", "parkInfoList", "flightList"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)	  
    @Column(name = "MEMBER_SEQ", length = 36)			
    private Long mid;											  

    private String id;
    private String name;  
    private String ename; //영문 이름
    private String rrnumber;	//주민등록번호
    private String password;
    private String address;
    private String detailAddress;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(columnDefinition = "varchar(255) default 'true'") // 'true'로 기본값 설정
    private String 	enabled;

    
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Board> boardList;


    
    
}
