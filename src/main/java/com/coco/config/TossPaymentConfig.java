package com.coco.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class TossPaymentConfig {

	@Value("${payment.client.key}")
	private String clientApiKey;
	
	@Value("${payment.success_url}")
	private String successUrl;
	
	@Value("${payment.fail_url}")
	private String failUrl;
	
	@Value("${payment.secret.key}")
	private String secretApiKey;
	
	public static final String URL = "https://api.tosspayments.com/v1/payments";
}
