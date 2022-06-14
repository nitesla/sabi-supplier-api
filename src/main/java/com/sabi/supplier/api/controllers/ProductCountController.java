package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.ProductCountService;
import com.sabi.suppliers.core.models.ProductCount;
import com.sabi.suppliers.core.models.response.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.APP_CONTENT+"productCount")
public class ProductCountController {

    @Autowired
    private ProductCountService service;

    @GetMapping("/name/{name}/supplierId/{supplierId}")
    public ResponseEntity<Response> getProduct(@PathVariable String name,
                                               @PathVariable Long supplierId){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductCount response = service.getAll(name,supplierId);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProduct(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductResponseDto response = service.findProductCountById(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
