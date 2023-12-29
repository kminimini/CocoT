package com.coco.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coco.domain.TrainTicketOrder;
import com.coco.repository.TrainTicketOrderRepository;

@RestController
@RequestMapping("/api")
public class TrainInfoController {
    private final Logger logger = LoggerFactory.getLogger(TrainInfoController.class);

    @Autowired
    private TrainTicketOrderRepository trainTicketOrderRepository;

    @PostMapping("/reserveTicket")
    public TrainTicketOrder reserveTicket(@RequestParam String trainTicketInfo) {
        // Generate a unique orderId
        String orderId = UUID.randomUUID().toString();

        // Create a TrainTicketOrder object
        TrainTicketOrder order = new TrainTicketOrder(orderId, trainTicketInfo);

        // Save the order in the database
        trainTicketOrderRepository.save(order);

        // Return the order object (you can customize this based on your needs)
        return order;
    }
}