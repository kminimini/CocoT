package com.coco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coco.domain.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

	Train findByTrainNo(String trainNo);
	
	Optional<Train> findByTrainNo(Long trainNo);

}
