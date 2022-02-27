package com.sabi.supplier.api.runner;



import com.sabi.framework.globaladminintegration.AccessTokenService;
import com.sabi.framework.service.ExternalTokenService;
import com.sabi.supplier.service.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@SuppressWarnings("ALL")
@Slf4j
@Service
@Component
public class CronJob {

    private ShipmentService shipmentService;
    private ExternalTokenService externalTokenService;
    private final AccessTokenService accessTokenService;

    public CronJob(ShipmentService shipmentService,ExternalTokenService externalTokenService,
                   AccessTokenService accessTokenService) {
        this.shipmentService = shipmentService;
        this.externalTokenService = externalTokenService;
        this.accessTokenService = accessTokenService;

    }


    @Scheduled(cron="${cronExpression}")
    @SchedulerLock(name = "ShipmentTripRequests", lockAtMostFor = "50s", lockAtLeastFor = "40s")
    public void ShipmentTripRequests() {
        log.info("move trip request Scheduler called", new Date());
        shipmentService.shipmentTripRequests();
    }


    @Scheduled(cron="${tokenGen}")
    @SchedulerLock(name = "getNewToken", lockAtMostFor = "40s", lockAtLeastFor = "30s")
    public void getNewToken() {
        log.info("::::::::::::: Cron Job Started at :::::::::::: :   %s", new Date());
        externalTokenService.externalTokenRequest();
    }


    @Scheduled(cron = "${globalAdminToken}")
    @SchedulerLock(name = "globalToken", lockAtMostFor = "30s", lockAtLeastFor = "20s")
    public void globalToken() {
        log.info("::::::::::::: global token Cron Job Started at :::::::::::: :   %s", new Date());
        accessTokenService.globalTokenRequest();
    }

}
