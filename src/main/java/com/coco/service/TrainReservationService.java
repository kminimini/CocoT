package com.coco.service;

public interface TrainReservationService {
    String retrieveOrderIdByorderId(String orderId);

	String generateOrderId(Long trainNo);
}
