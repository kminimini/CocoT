package com.coco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coco.domain.Train;

/* 열차정보에 대한 처리 */

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

	Train findByTrainNo(String trainNo);
	
	Optional<Train> findByTrainNo(Long trainNo);

	Train findByOrderId(String orderId);
	
}
