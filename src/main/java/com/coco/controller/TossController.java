package com.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coco.domain.PaymentInfo;
import com.coco.service.TrainReservationService;

@Controller
@RequestMapping("/toss")
public class TossController {
	
	@Autowired
	private TrainReservationService trainReservationService;

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
	public String paymentSuccess(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam long amount, Model model) {
	    model.addAttribute("paymentKey", paymentKey);
	    model.addAttribute("orderId", orderId);
	    model.addAttribute("amount", amount);
	    return "toss/success";
	}

    // 실패 페이지에 대한 처리기 추가
    @GetMapping("/fail")
    public String paymentFail() {
        return "toss/fail";
    }
    
    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam long amount) {
        System.out.println("confirmPayment메서드 실행중.......");
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(orderId);
        paymentInfo.setPaymentKey(paymentKey);
        paymentInfo.setAmount(amount);
        trainReservationService.savePaymentInfo(paymentInfo);

        // Continue with the rest of the payment initiation code
        return "redirect:/toss/success?orderId=" + orderId + "&paymentKey=" + paymentKey + "&amount=" + amount;
    }
}