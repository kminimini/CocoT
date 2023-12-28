package com.coco.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPLY_SEQ", length = 36)
    private Long rseq;

    @ManyToOne
    @JoinColumn(name = "BOARD_BSEQ")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    private String rcontent;
    
    // 사용자 이름을 저장할 변수 추가
    @Transient
    private String username;

    // 생성자 등 코드 수정 생략...

}