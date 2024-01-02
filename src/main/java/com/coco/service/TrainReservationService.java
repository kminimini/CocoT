package com.coco.service;

import com.coco.domain.PaymentInfo;

public interface TrainReservationService {
	
	// 결제를 위한 orderId를 가져옴
    String retrieveOrderIdByorderId(String orderId);
    
    // 결제요청한 데이터를 저장한다.
    void savePaymentInfo(PaymentInfo paymentInfo);

    void savePaymentInfo(String orderId, Long amount, String paymentKey);

    String confirmTossPayment(String orderId, String paymentKey, Long amount);
}
