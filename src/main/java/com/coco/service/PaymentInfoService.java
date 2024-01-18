package com.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coco.domain.PaymentInfo;
import com.coco.repository.PaymentInfoRepository;

import java.util.Optional;

@Service
public class PaymentInfoService {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    public void save(PaymentInfo paymentInfo) {
        paymentInfoRepository.save(paymentInfo);
    }

    public Optional<PaymentInfo> findByOrderId(String orderId) {
        return paymentInfoRepository.findByOrderId(orderId);
    }
    
    public void updatePaymentInfo(String orderId, Long amount, String paymentKey) {
        Optional<PaymentInfo> optionalPaymentInfo = paymentInfoRepository.findByOrderId(orderId);
        if (optionalPaymentInfo.isPresent()) {
            PaymentInfo paymentInfo = optionalPaymentInfo.get();
            paymentInfo.setAmount(amount);
            paymentInfo.setPaymentKey(paymentKey);
            paymentInfoRepository.save(paymentInfo);
        } else {
        }
    }

	public void savePaymentInfo(PaymentInfo paymentInfo) {
		paymentInfoRepository.save(paymentInfo);
		
	}
}