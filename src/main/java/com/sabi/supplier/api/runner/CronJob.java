package com.sabi.supplier.api.runner;



import com.sabi.framework.service.ExternalTokenService;
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
    private ExternalTokenService externalTokenService;

    public CronJob(ShipmentService shipmentService,ExternalTokenService externalTokenService) {
        this.shipmentService = shipmentService;
        this.externalTokenService = externalTokenService;

    }


    @Scheduled(cron="${cronExpression}")
    public void ShipmentTripRequests() {
        log.info("move trip request Scheduler called", new Date());
        shipmentService.shipmentTripRequests();
    }


    @Scheduled(cron="${tokenGen}")
    public void getNewToken() {
        log.info("::::::::::::: Cron Job Started at :::::::::::: :   %s", new Date());
        externalTokenService.externalTokenRequest();
    }


}
