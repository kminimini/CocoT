package com.coco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coco.domain.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

	Train findByTrainNo(String trainNo);
	
	Optional<Train> findByTrainNo(Long trainNo);

	Train findByOrderId(String orderId);
	
}
