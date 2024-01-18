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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coco.domain.PaymentInfo;
import com.coco.service.PaymentInfoService;
import com.coco.service.TrainReservationService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PaymentViewController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${payment.client.key}")
	private String clientApiKey;
	
	@Value("${payment.secret.key}")
	private String secretApiKey;
	
	@Value("${payment.success_url}")
	private String successUrl;
	
	@Value("${payment.fail_url}")
	private String failUrl;
	
	private final PaymentInfoService paymentInfoService;
	
	private final TrainReservationService trainReservationService;
	
	private static final String TOSS_API_URL = "https://api.tosspayments.com/v1/payments/confirm";
	
	@GetMapping("/success")
	public String handleSuccessRedirect(
	        @RequestParam String paymentType,
	        @RequestParam String orderId,
	        @RequestParam String paymentKey,
	        @RequestParam Long amount,
	        Model model) {

	    // 임시 결제 정보 검색
	    Optional<PaymentInfo> optionalPaymentInfo = paymentInfoService.findByOrderId(orderId);
	    if (optionalPaymentInfo.isEmpty()) {
	        // 오류 처리, 임시 결제 정보를 찾을 수 없음
	        return "toss/fail";
	    }

	    PaymentInfo storedPaymentInfo = optionalPaymentInfo.get();

	    // 수신한 정보가 저장된 정보와 일치하는지 확인합니다.
	    if (!storedPaymentInfo.getAmount().equals(amount)) {
	        // 처리 오류, 금액이 일치하지 않음
	        return "toss/fail";
	    }

	    // 필요에 따라 추가 유효성 검사 수행(예: 쿠폰, 로열티 포인트)

	    // 모든 유효성 검사를 통과하면 결제 확인을 진행합니다.
	    String successWidgetInfo = prepareSuccessWidgetInfo(paymentKey, orderId, amount);

	    // 결제 정보를 모델에 추가
	    model.addAttribute("successWidgetInfo", successWidgetInfo);

	    // success view로 이동
	    return "toss/success";
	}
	
	@PostMapping("/store-payment-info")
    public ResponseEntity<String> storePaymentInfo(@RequestParam String orderId, @RequestParam Long amount) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(orderId);
        paymentInfo.setAmount(amount);

        // 서비스를 사용하여 결제 정보를 데이터베이스에 저장
        trainReservationService.savePaymentInfo(paymentInfo);

        return ResponseEntity.ok("PaymentInfo stored successfully");
    }
	
	@PostMapping("/confirm")
	public ResponseEntity<String> confirmPayment(@RequestBody String jsonBody) {
	    JSONParser parser = new JSONParser();
	    String orderId;
	    Long amount;
	    String paymentKey;

	    try {
	        JSONObject requestData = (JSONObject) parser.parse(jsonBody);
	        paymentKey = (String) requestData.get("paymentKey");
	        orderId = (String) requestData.get("orderId");
	        amount = Long.parseLong((String) requestData.get("amount"));
	    } catch (ParseException | NumberFormatException e) {
	        return ResponseEntity.badRequest().body("Error parsing JSON body");
	    }

	    // 수신된 값으로 PaymentInfo 엔티티를 업데이트합니다.
	    Optional<PaymentInfo> optionalPaymentInfo = paymentInfoService.findByOrderId(orderId);

	    if (optionalPaymentInfo.isPresent()) {
	        PaymentInfo paymentInfo = optionalPaymentInfo.get();
	        paymentInfo.setPaymentKey(paymentKey);
	        paymentInfo.setAmount(amount);

	        // 업데이트된 결제 정보 엔티티 저장
	        paymentInfoService.save(paymentInfo);

	        // 결제 확인을 위해 토스 결제 API 호출하기
	        ResponseEntity<String> paymentConfirmationResponse = completePayment(paymentKey, orderId, amount);

	        // Toss 결제 API의 응답 확인
	        if (paymentConfirmationResponse.getStatusCode().is2xxSuccessful()) {
	            String successWidgetInfo = prepareSuccessWidgetInfo(paymentKey, orderId, amount);

	            // TODO: 결제 성공 시에만 로깅하도록 수정
	            logger.info("Payment confirmation successful for orderId: {}", orderId);

	            // 결제가 성공적으로 완료되었습니다.
	            return ResponseEntity.ok(successWidgetInfo);
	        } else {
	            // TODO: 결제 실패 시에만 로깅하도록 수정
	            logger.error("Error completing payment for orderId: {}, Reason: {}", orderId, paymentConfirmationResponse.getBody());

	            // Toss 결제 API에서 발생한 오류 처리
	            return ResponseEntity.status(paymentConfirmationResponse.getStatusCode())
	                    .body("Error completing payment: " + paymentConfirmationResponse.getBody());
	        }
	    } else {
	        return ResponseEntity.badRequest().body("PaymentInfo not found for orderId: " + orderId);
	    }
	}
	
	private String prepareSuccessWidgetInfo(String paymentKey, String orderId, Long amount) {
	    // "/success"가 위젯이 포함된 결제 성공 페이지가 렌더링되는 URL이라고 가정합니다.
	    String successUrl = "/success?orderId=" + orderId + "&amount=" + amount + "&paymentKey=" + paymentKey;
	    
	    return successUrl;
	}
	private ResponseEntity<String> completePayment(String paymentKey, String orderId, Long amount) {
        try {
            HttpHeaders headers = new HttpHeaders();

            // 기본 인증을 위한 API 키를 Base64로 인코딩
            String encodedApiKey = Base64.getEncoder().encodeToString((secretApiKey + ":").getBytes("UTF-8"));
            headers.set("Authorization", "Basic " + encodedApiKey);
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("paymentKey", paymentKey);
            requestBody.put("orderId", orderId);
            requestBody.put("amount", amount);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // URL을 직접 지정하세요.
            String tossApiUrl = TOSS_API_URL;

            // HTTP POST 요청하기
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(tossApiUrl, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error completing payment");
        }
    }
}
