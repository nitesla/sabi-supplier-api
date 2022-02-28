package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.supplier.service.services.AnalyticsRouteService;
import com.sabi.suppliers.core.dto.request.AnalyticsRouteRequest;
import com.sabi.suppliers.core.models.response.AnalyticRouteResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Constants.APP_CONTENT +"route")
public class AnalyticsRouteController {


    private final AnalyticsRouteService analyticsRouteService;

    public AnalyticsRouteController(AnalyticsRouteService analyticsRouteService) {
        this.analyticsRouteService = analyticsRouteService;
    }

    @PostMapping("")
    AnalyticRouteResponseDto analyticRouteCheck(@RequestBody AnalyticsRouteRequest request){
        AnalyticRouteResponseDto verify = analyticsRouteService.analyticsRouteCheck(request);
        return verify;
    }

//    public ResponseEntity<Response> createCountry(@Validated @RequestBody AnalyticsRouteRequest request){
//        HttpStatus httpCode ;
//        Response resp = new Response();
//        AnalyticRouteResponseDto response = analyticsRouteService.
//        resp.setCode(CustomResponseCode.SUCCESS);
//        resp.setDescription("Successful");
//        resp.setData(response);
//        httpCode = HttpStatus.CREATED;
//        return new ResponseEntity<>(resp, httpCode);
////    }
}
