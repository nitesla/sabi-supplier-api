package com.sabi.supplier.api.helper;


import com.sabi.supplier.service.services.SupplyRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CronJobRunner {

    private SupplyRequestService supplyRequestService;

    public CronJobRunner(SupplyRequestService supplyRequestService) {
        this.supplyRequestService = supplyRequestService;
    }

    // @Scheduled(cron="0 0 0 * * ?")
    @Scheduled(cron="${cronExpressionSupplier}")
    public void expireAcceptedAndExpiredTrips() {
        log.info("::::::::::::: Cron Job Started at :::::::::::: :   %s", new Date());
        supplyRequestService.pushToIncomingRequest();
    }
}
