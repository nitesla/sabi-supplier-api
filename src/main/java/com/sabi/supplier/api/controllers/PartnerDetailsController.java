package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.PartnerSignUpService;
import com.sabi.suppliers.core.dto.response.ExternalDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"partnerdetails")
public class PartnerDetailsController {


    private final PartnerSignUpService service;


    public PartnerDetailsController(PartnerSignUpService service) {
        this.service = service;
    }


    @GetMapping("")
    public ResponseEntity<Response> getPartnerDetails(){
        HttpStatus httpCode ;
        Response resp = new Response();
        ExternalDetailsResponse response = service.partnerDetails();
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
