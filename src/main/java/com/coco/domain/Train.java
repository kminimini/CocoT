package com.coco.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String orderId;
    
	private Long trainNo;

    private Long depplandtime;		// 출발시간

    private Long arrplandtime;		// 도착시간
    
    private String depplacename;		// 출발지

    private String arrplacename;		// 도착지

    private String traingradename;
    
    private Long adultcharge;
        
}
