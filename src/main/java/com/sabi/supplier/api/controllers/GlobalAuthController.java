package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.GlobalAdminAuthService;
import com.sabi.suppliers.core.dto.request.GlobalAdminAuthRequestDto;
import com.sabi.suppliers.core.models.response.GlobalAdminAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"globalAuth")
public class GlobalAuthController {

    @Autowired
    GlobalAdminAuthService service;

    @PostMapping("")
    public ResponseEntity<Response> globalAdminAuthUser(@Validated @RequestBody GlobalAdminAuthRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        GlobalAdminAuthResponse response = service.authenticateUser(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }
}
