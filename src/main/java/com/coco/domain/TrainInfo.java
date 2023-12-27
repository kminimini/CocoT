package com.coco.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrainInfo {

	private int trainNo;
	private String trainType;
	private String departureTime;
	private String arrivalTime;
	private int price;
}
