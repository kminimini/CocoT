package com.coco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coco.domain.PaymentInfo;
import com.coco.service.TrainReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/toss")
public class TossController {	
	private final TrainReservationService trainReservationService;
	
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

        // 결제 세부 정보를 데이터베이스에 저장
		trainReservationService.savePaymentInfo(orderId, amount, paymentKey);

        // Toss로 결제 확인
        String tossConfirmationResult = trainReservationService.confirmTossPayment(orderId, paymentKey, amount);

        if ("success".equals(tossConfirmationResult)) {
            // 성공 비즈니스 로직을 구현하거나 성공 페이지로 리디렉션하기
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            return "toss/success";
        } else {
            return "toss/fail";
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

        // 서비스를 사용하여 결제 정보를 데이터베이스에 저장합니다.
        trainReservationService.savePaymentInfo(paymentInfo);

        return ResponseEntity.ok("PaymentInfo stored successfully");
    }
}