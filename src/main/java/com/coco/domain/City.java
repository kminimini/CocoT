package com.coco.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cityCode;
	private String cityName;
//	private int resultCode;
//	private String resultMsg;
}
