package com.coco.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

	
	// 추가: 생성자
    public Board(Long bseq) {
        this.bseq = bseq;
    }

}
