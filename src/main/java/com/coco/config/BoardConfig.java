package com.coco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coco.service.BoardService;
import com.coco.service.BoardServiceImpl;

@Configuration
public class BoardConfig {

	@Bean
	public BoardService boardService() {
		return new BoardServiceImpl();
	}
}
