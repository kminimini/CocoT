package com.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coco.domain.PaymentInfo;
import com.coco.repository.PaymentInfoRepository;
import com.coco.service.PaymentInfoService;
import com.coco.service.TrainReservationService;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/toss")
public class TossController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final TrainReservationService trainReservationService;

    private final PaymentInfoRepository paymentInfoRepository;
	
	private final PaymentInfoService paymentInfoService;
	
	@GetMapping("/checkout.html")
	public String showCheckoutPage(@RequestParam String orderId, Model model) {
	    System.out.println("Received orderId: " + orderId);
	    
	    // 데이터베이스에서 orderId를 검색
	    String retrievedOrderId = trainReservationService.retrieveOrderIdByorderId(orderId);

	    model.addAttribute("orderId", retrievedOrderId);

	    return "toss/checkout";
	}
	
	// 성공 페이지에 대한 핸들러 추가
	@GetMapping("/success")
    public String handleSuccess(
            @RequestParam String paymentType,
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam Long amount,
            Model model) {

        // Save payment details to database
		trainReservationService.savePaymentInfo(orderId, amount, paymentKey);

        // Confirm payment with Toss
        String tossConfirmationResult = trainReservationService.confirmTossPayment(orderId, paymentKey, amount);

        if ("success".equals(tossConfirmationResult)) {
            // Implement success business logic or redirect to a success page
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            return "toss/success"; // Assuming you have a success.html template
        } else {
            // Implement failure business logic or redirect to a failure page
            return "toss/fail"; // Assuming you have a fail.html template
        }
    }
	
    // 실패 페이지에 대한 처리기 추가
    @GetMapping("/fail")
    public String paymentFail() {
        return "toss/fail";
    }

    // 결제요청 정보를 PaymentInfo에 저장
    @PostMapping("/store-payment-info")
    public ResponseEntity<String> storePaymentInfo(@RequestParam String orderId, @RequestParam Long amount) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(orderId);
        paymentInfo.setAmount(amount);

        // Save the paymentInfo to the database using your service
        trainReservationService.savePaymentInfo(paymentInfo);

        return ResponseEntity.ok("PaymentInfo stored successfully");
    }
}