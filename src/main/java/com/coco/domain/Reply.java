package com.coco.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "PARENT_REPLY_SEQ")  // 추가: 부모 댓글을 참조하기 위한 필드
    private Reply parentReply;
    
    // 자식 댓글 목록
    @OneToMany(mappedBy = "parentReply")
    private Set<Reply> children;

}