package com.coco.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

/*
 * 항공편 정보 En
 */

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "train")
public class Train {

    @Id
    @Column(name = "trainNumber")
    private int trainNumber;			// 열차번호
    
    private String trainName;		// 기차종류

    private Date depPlandTime;		// 출발시간

    private Date arrPlandTime;		// 도착시간

    private int economyCharge;		// 일반석운임

    private int prestigeCharge;		// 비즈니스석운임
    
    private String depPlaced;		// 출발지

    private String arrPlace;		// 도착지

    private String startStationName;	// input 출발역

    private String endStationName;		// input 도착역

    private Date depSelectTime;		// 조회 날짜 - 출발

    private Integer pageNo;			// 페이지 번호

    private int totalCount;			// item count

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ", nullable = false, updatable = false)
    private Member member;
    
	
}
