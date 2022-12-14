package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierLocationService;
import com.sabi.suppliers.core.dto.request.SupplierLocationRequestDto;
import com.sabi.suppliers.core.dto.response.SupplierLocationResponseDto;
import com.sabi.suppliers.core.models.SupplierLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"supplierlocation")
public class SupplierLocationController {


    private final SupplierLocationService service;

    public SupplierLocationController(SupplierLocationService service) {
        this.service = service;
    }


    /** <summary>
     * Supplier Location creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Supplier Location</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createSupplierLocation(@Validated @RequestBody SupplierLocationRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierLocationResponseDto response = service.createSupplierLocation(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supplier Location update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Supplier Location</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplierLocation(@Validated @RequestBody  SupplierLocationRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierLocationResponseDto response = service.updateSupplierLocation(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Get single record endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting a single record</remarks>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSupplierLocation(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierLocationResponseDto response = service.findSupplierLocation(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("")
    public ResponseEntity<Response> getSupplierLocations(@RequestParam(value = "supplierId",required = false)Long supplierId,
                                                     @RequestParam(value = "stateId",required = false)Long stateId,
                                                         @RequestParam(value = "stateName",required = false)String stateName,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<SupplierLocation> response = service.findAll(supplierId, stateId,stateName, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Supplier Location</remarks>
     */

    @PutMapping("/enabledisable")
    public ResponseEntity<Response> enableDisable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisable(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<SupplierLocation> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


}
