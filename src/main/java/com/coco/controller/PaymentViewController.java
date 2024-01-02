package com.coco.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.coco.domain.PaymentInfo;
import com.coco.service.PaymentInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PaymentViewController {
	
	@Value("${payment.client.key}")
	private String clientApiKey;
	
	@Value("${payment.secret.key}")
	private String secretApiKey;
	
	private final PaymentInfoService paymentInfoService;
	

	@GetMapping("/success")
    public String handleSuccessRedirect(
            @RequestParam String paymentType,
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam Long amount) {

        // Retrieve temporary payment information
        Optional<PaymentInfo> optionalPaymentInfo = paymentInfoService.findByOrderId(orderId);
        if (optionalPaymentInfo.isEmpty()) {
            // Handle error, temporary payment information not found
            return "toss/fail";
        }

        PaymentInfo storedPaymentInfo = optionalPaymentInfo.get();

        // Validate if the received information matches the stored information
        if (!storedPaymentInfo.getAmount().equals(amount)) {
            // Handle error, amounts do not match
            return "toss/fail";
        }

        // Perform additional validation as needed (e.g., coupon, loyalty points)

        // If all validations pass, proceed with payment confirmation
        return "toss/success";
    }
}
