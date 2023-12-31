package com.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coco.domain.TrainReservation;
import com.coco.repository.TrainReservationRepository;

@Service
public class TrainReservationServiceImpl implements TrainReservationService {

	@Autowired
	private TrainReservationRepository trainReservationRepository;
	
	@Override
    public String retrieveOrderIdByCustomerKey(String orderId) {
		// 고객키를 기반으로 데이터베이스에서 orderId를 검색하는 로직을 구현합.
        // 엔티티 구조와 리포지토리 메서드에 따라 이를 사용자 정의해야 할 수 있음.
        // 예를 들어 TrainReservation 엔티티에 'orderId' 필드가 있고 findByorderId 메서드가 있다고 가정.
        TrainReservation trainReservation = trainReservationRepository.findByorderId(orderId);
        
        // trainReservation이 null이 아닌지 확인하고 orderId를 반환
        return (trainReservation != null) ? trainReservation.getOrderId() : null;
    }

}
