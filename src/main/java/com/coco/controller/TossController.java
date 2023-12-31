package com.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coco.service.TrainReservationService;

@Controller
@RequestMapping("/toss")
public class TossController {
	
	@Autowired
	private TrainReservationService trainReservationService;

	@GetMapping("/checkout.html")
	public String showCheckoutPage(@RequestParam String orderId, Model model) {
		// URL에서 주문 ID가 올바르게 수신되었는지 확인합니다.
	    System.out.println("Received orderId: " + orderId);
	    
	    // 고객키를 기준으로 데이터베이스에서 orderId를 검색
	    String retrievedOrderId = trainReservationService.retrieveOrderIdByorderId(orderId);

	    // 주문 ID를 뷰에 전달
	    model.addAttribute("orderId", retrievedOrderId);

	    return "toss/checkout";
	}
	
	// 성공 페이지에 대한 핸들러 추가
    @GetMapping("/success")
    public String paymentSuccess() {
        return "toss/success";
    }

    // 실패 페이지에 대한 처리기 추가
    @GetMapping("/fail")
    public String paymentFail() {
        return "toss/fail";
    }
    
    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam long amount) {
    	// 여기에서 결제 확인 로직을 수행.
        // 결제 정보를 기반으로 데이터베이스의 TrainReservation 엔티티를 업데이트.
        // 그에 따라 성공 또는 실패 페이지로 리디렉션.

        return "redirect:/payment/success";
    }
}