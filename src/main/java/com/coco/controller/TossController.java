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
import com.coco.service.TrainReservationService;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Controller
@RequestMapping("/toss")
public class TossController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TrainReservationService trainReservationService;

	@Autowired
    private PaymentInfoRepository paymentInfoRepository;
	
	@GetMapping("/checkout.html")
	public String showCheckoutPage(@RequestParam String orderId, Model model) {
	    System.out.println("Received orderId: " + orderId);
	    
	    // 데이터베이스에서 orderId를 검색
	    String retrievedOrderId = trainReservationService.retrieveOrderIdByorderId(orderId);

	    model.addAttribute("orderId", retrievedOrderId);

	    return "toss/checkout";
	}
	
	// 성공 페이지에 대한 핸들러 추가
	@RequestMapping(value = "/success")
    public String successPage(@RequestParam("paymentType") String paymentType,
                              @RequestParam("orderId") String orderId,
                              @RequestParam("amount") Long amount,
                              Model model) {

        logger.info("Payment success - Order ID: {}, Payment Key: {}, Amount: {}", orderId, amount);

        // Save payment details to PaymentInfo entity
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(orderId);
        paymentInfo.setAmount(amount);

        trainReservationService.savePaymentInfo(paymentInfo); // Assuming you have a save method in your service

        // Add attributes to the model if needed
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "toss/success"; // Assuming your success page is named "success.html"
    }
	
    // 실패 페이지에 대한 처리기 추가
    @GetMapping("/fail")
    public String paymentFail() {
        return "toss/fail";
    }

}