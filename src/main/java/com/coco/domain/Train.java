package com.coco.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

/*
 * 열차 정보 En
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "order_id")
	private String orderId;			// 고유 번호 (자동생성값)
    
	private Long trainNo;			// 열차 번호

	@Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime depplandtime;    // 출발시간

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime arrplandtime;    // 도착시간
    
    private String depplacename;	// 출발지

    private String arrplacename;	// 도착지

    private String traingradename;	// 열차 정보
    
    @Column(name = "amount")
    private Long adultcharge;		// 요금
        
}
