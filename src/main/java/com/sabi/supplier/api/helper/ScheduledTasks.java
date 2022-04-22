package com.sabi.supplier.api.helper;


import com.sabi.supplier.service.services.SupplyRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created by Muyiwa Akanni on 21/004/2022.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

private final SupplyRequestService supplyRequestService;

    @Scheduled(fixedDelayString = "${supply.request.expired.time}")
    private void expireAcceptedAndExpiredTrips() throws Exception{
        log.info("Getting ready to expire unaccepted supply request.");
        supplyRequestService.pushToIncomingRequest();
    }


}
