package com.coco.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.coco.domain.PaymentInfo;
import com.coco.service.PaymentInfoService;
import com.coco.service.TrainReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
@RestController
public class PaymentController {
	
	@Value("${payment.client.key}")
	private String clientApiKey;
	
	@Value("${payment.secret.key}")
	private String secretApiKey;
	
	private static final String TOSS_API_URL = "https://api.tosspayments.com/v1/payments/confirm";

    @Autowired
    private TrainReservationService trainReservationService;  // Make sure to autowire your service
    
    @Autowired
    private PaymentInfoService paymentInfoService;
    
    @PostMapping("/store-payment-info")
    public ResponseEntity<String> storePaymentInfo(@RequestParam String orderId, @RequestParam Long amount) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(orderId);
        paymentInfo.setAmount(amount);

        // Save the paymentInfo to the database using your service
        trainReservationService.savePaymentInfo(paymentInfo);

        return ResponseEntity.ok("PaymentInfo stored successfully");
    }
    
    @GetMapping("/success")
    public ResponseEntity<?> handleSuccessRedirect(
            @RequestParam String paymentType,
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam Long amount) {

        // Retrieve temporary payment information
        Optional<PaymentInfo> optionalPaymentInfo = paymentInfoService.findByOrderId(orderId);
        if (optionalPaymentInfo.isEmpty()) {
            // Handle error, temporary payment information not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Temporary payment information not found");
        }

        PaymentInfo storedPaymentInfo = optionalPaymentInfo.get();

        // Validate if the received information matches the stored information
        if (!storedPaymentInfo.getAmount().equals(amount)) {
            // Handle error, amounts do not match
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amounts do not match");
        }

        // Perform additional validation as needed (e.g., coupon, loyalty points)

        // If all validations pass, proceed with payment confirmation
        return confirmPayment(paymentKey, orderId, amount);
    }
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody String jsonBody) {
        JSONParser parser = new JSONParser();
        String orderId;
        Long amount;
        String paymentKey;

        try {
            // Parse the JSON request body
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (Long) requestData.get("amount");
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Error parsing JSON body");
        }

        // Update the PaymentInfo entity with the received values
        Optional<PaymentInfo> optionalPaymentInfo = paymentInfoService.findByOrderId(orderId);

        if (optionalPaymentInfo.isPresent()) {
            PaymentInfo paymentInfo = optionalPaymentInfo.get();
            paymentInfo.setPaymentKey(paymentKey);
            paymentInfo.setAmount(amount);

            // Save the updated PaymentInfo entity
            paymentInfoService.save(paymentInfo);

            // Call Toss Payments API for payment confirmation
            ResponseEntity<String> paymentConfirmationResponse = completePayment(paymentKey, orderId, amount);

            // Check the response from Toss Payments API
            if (paymentConfirmationResponse.getStatusCode().is2xxSuccessful()) {
            	
            	String successWidgetInfo = prepareSuccessWidgetInfo(paymentKey, orderId, amount);
                // Payment completed successfully
                return ResponseEntity.ok(successWidgetInfo);
            } else {
                // Handle error from Toss Payments API
                return ResponseEntity.status(paymentConfirmationResponse.getStatusCode())
                        .body("Error completing payment: " + paymentConfirmationResponse.getBody());
            }
        } else {
            // Handle error, PaymentInfo not found
            return ResponseEntity.badRequest().body("PaymentInfo not found for orderId: " + orderId);
        }
    }
    private ResponseEntity<?> confirmPayment(String paymentKey, String orderId, Long amount) {
        try {
            String tossApiUrl = "https://api.tosspayments.com/v1/payments/confirm";

            HttpHeaders headers = new HttpHeaders();

            // Base64 encode the API key for Basic authentication
            String encodedApiKey = Base64.getEncoder().encodeToString((secretApiKey + ":").getBytes("UTF-8"));
            headers.set("Authorization", "Basic " + encodedApiKey);

            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("paymentKey", paymentKey);
            requestBody.put("orderId", orderId);
            requestBody.put("amount", amount);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    tossApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Handle the response as needed
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
            	// Payment confirmed successfully
                // You can add logic here to prepare the success widget information
                String successWidgetInfo = prepareSuccessWidgetInfo(paymentKey, orderId, amount);
                
                // Return the success widget information
                return ResponseEntity.ok(successWidgetInfo);
            } else {
                // Handle payment confirmation failure
                return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
            }
        } catch (Exception e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 확인 중 오류 발생");
        }
    }
    
    private String prepareSuccessWidgetInfo(String paymentKey, String orderId, Long amount) {
        // Add logic here to prepare the success widget information
        // For example, you can create a JSON object or string with the required information
        Map<String, Object> successInfo = new HashMap<>();
        successInfo.put("orderId", orderId);
        successInfo.put("amount", amount);
        successInfo.put("paymentKey", paymentKey);

        // Convert the map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(successInfo);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            e.printStackTrace();
            return "{}";
        }
    }
    
    private ResponseEntity<String> completePayment(String paymentKey, String orderId, Long amount) {
        try {
            HttpHeaders headers = new HttpHeaders();

            // Base64 encode the API key for Basic authentication
            String encodedApiKey = Base64.getEncoder().encodeToString((secretApiKey + ":").getBytes("UTF-8"));
            headers.set("Authorization", "Basic " + encodedApiKey);
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("paymentKey", paymentKey);
            requestBody.put("orderId", orderId);
            requestBody.put("amount", amount);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Specify the URL directly
            String tossApiUrl = "https://api.tosspayments.com/v1/payments/confirm";

            // Make the HTTP POST request
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(tossApiUrl, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error completing payment");
        }
    }
}