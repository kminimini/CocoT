package com.coco.service;

public interface TrainReservationService {
	
	// 결제를 위한 orderId를 가져옴
    String retrieveOrderIdByorderId(String orderId);
}
