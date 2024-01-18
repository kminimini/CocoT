package com.coco.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Board")
public class Board {

	@Id
	@GeneratedValue
	private Long bseq;

	private String btitle;
	private String bcontent;

	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;

	@Column(insertable = false, columnDefinition = "number default 0")
	private Long cnt;

	@ManyToOne
	@JoinColumn(name = "MEMBER_SEQ", nullable = false, updatable = true)
	private Member member;

    public Board(Long bseq) {
        this.bseq = bseq;
    }
    
    // 비밀글로 인한 엔터티 추가
    @Column(columnDefinition = "NUMBER(1)")
    private boolean secret;
    
    // 비밀글일 경우 비밀번호 필드
    private String secretPassword;
    
    // 비밀글 여부 확인 메서드
    public boolean isSecret() {
        return secret;
    }
    
    // 댓글
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies;

}
