package com.coco.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coco.domain.PaymentInfo;
import com.coco.domain.TrainReservation;
import com.coco.repository.PaymentInfoRepository;
import com.coco.repository.TrainReservationRepository;

@Service
public class TrainReservationServiceImpl implements TrainReservationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TrainReservationRepository trainReservationRepository;
	
	@Autowired
    private PaymentInfoRepository paymentInfoRepository;
	
	/* TODO 결제를 위한 orderId를 가져옴 */
	@Override
    public String retrieveOrderIdByorderId(String orderId) {
		logger.info("다음에 대해 데이터베이스에서 orderId를 검색합니다. orderId: {}", orderId);
		
        TrainReservation trainReservation = trainReservationRepository.findByOrderId(orderId);
        
        String retrievedOrderId = (trainReservation != null) ? trainReservation.getOrderId() : null;

        logger.info("데이터베이스에서 주문 ID 검색: {}", retrievedOrderId);
        
        return (trainReservation != null) ? trainReservation.getOrderId() : null;
    }
	
	@Transactional
	@Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
		try {
	        paymentInfoRepository.save(paymentInfo);
	        logger.info("결제 정보 저장 성공: {}", paymentInfo);
	    } catch (Exception e) {
	        logger.error("결제 정보 저장 오류: {}", e.getMessage(), e);
	        throw e;
	    }
    }
	
	@Override
    public void savePaymentInfo(String orderId, Long amount, String paymentKey) {
		try {
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOrderId(orderId);
            paymentInfo.setAmount(amount);
            paymentInfo.setPaymentKey(paymentKey);

            savePaymentInfoToRepository(paymentInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error saving payment information", e);
        }
    }
	
	private void savePaymentInfoToRepository(PaymentInfo paymentInfo) {
        paymentInfoRepository.save(paymentInfo);
    }
	
    @Override
    public String confirmTossPayment(String orderId, String paymentKey, Long amount) {
        try {
            return "toss/success";
        } catch (Exception e) {
            return "toss/fail";
        }
    }
}
