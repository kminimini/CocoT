package com.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coco.domain.TrainReservation;

/* 열차정보 결제처리 */

@Repository
public interface TrainReservationRepository extends JpaRepository<TrainReservation, Long> {

	TrainReservation findByOrderId(String orderId);
}
