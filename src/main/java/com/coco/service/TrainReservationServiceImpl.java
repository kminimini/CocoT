package com.coco.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coco.domain.TrainReservation;
import com.coco.repository.TrainReservationRepository;

@Service
public class TrainReservationServiceImpl implements TrainReservationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TrainReservationRepository trainReservationRepository;
	
	@Override
    public String retrieveOrderIdByorderId(String orderId) {
		// 고객키를 기반으로 데이터베이스에서 orderId를 검색하는 로직을 구현합.
        // 엔티티 구조와 리포지토리 메서드에 따라 이를 사용자 정의해야 할 수 있음.
        // 예를 들어 TrainReservation 엔티티에 'orderId' 필드가 있고 findByorderId 메서드가 있다고 가정.
		logger.info("Retrieving orderId from the database for orderId: {}", orderId);

		
        TrainReservation trainReservation = trainReservationRepository.findByOrderId(orderId);
        
        String retrievedOrderId = (trainReservation != null) ? trainReservation.getOrderId() : null;

        logger.info("Retrieved orderId from the database: {}", retrievedOrderId);
        
        // trainReservation이 null이 아닌지 확인하고 orderId를 반환
        return (trainReservation != null) ? trainReservation.getOrderId() : null;
    }
	
	@Override
    public String generateOrderId(Long trainNo) {
		// trainNo를 기반으로 주문 ID를 생성하는 로직을 구현합니다.
        // 여기에는 데이터베이스 쿼리, 고유 식별자 생성 등이 포함될 수 있습니다.
        // 간단하게 하기 위해 trainNo와 현재 타임스탬프의 기본 연결을 사용하겠습니다.
        return trainNo + "_" + System.currentTimeMillis();
    }
}
