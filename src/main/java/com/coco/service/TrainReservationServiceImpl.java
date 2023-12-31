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
	
	/* TODO 결제를 위한 orderId를 가져옴 */
	@Override
    public String retrieveOrderIdByorderId(String orderId) {
		logger.info("Retrieving orderId from the database for orderId: {}", orderId);

		
        TrainReservation trainReservation = trainReservationRepository.findByOrderId(orderId);
        
        String retrievedOrderId = (trainReservation != null) ? trainReservation.getOrderId() : null;

        logger.info("Retrieved orderId from the database: {}", retrievedOrderId);
        
        return (trainReservation != null) ? trainReservation.getOrderId() : null;
    }
}
