package com.sabi.supplier.api.runner;



import com.sabi.supplier.service.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class CronJob {

    private ShipmentService shipmentService;

    public CronJob(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;

    }


    @Scheduled(cron="${cronExpression}")
    public void ShipmentTripRequests() {
        log.info("move trip request Scheduler called", new Date());
        shipmentService.shipmentTripRequests();
    }



}
