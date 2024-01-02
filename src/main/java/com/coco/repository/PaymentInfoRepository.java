package com.coco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coco.domain.PaymentInfo;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

	Optional<PaymentInfo> findByOrderId(String orderId);

}