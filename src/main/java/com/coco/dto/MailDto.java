package com.coco.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailDto {

	private String title;
	private String content;
	private String recipient;
	
}
