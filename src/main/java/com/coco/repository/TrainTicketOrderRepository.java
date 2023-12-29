package com.coco.repository;

import org.springframework.data.repository.CrudRepository;

import com.coco.domain.TrainTicketOrder;

public interface TrainTicketOrderRepository extends CrudRepository<TrainTicketOrder, String> {

}
