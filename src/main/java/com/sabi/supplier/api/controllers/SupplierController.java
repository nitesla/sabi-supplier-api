package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierService;
import com.sabi.suppliers.core.dto.request.SupplierRequestDto;
import com.sabi.suppliers.core.dto.response.SupplierResponseDto;
import com.sabi.suppliers.core.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"supplier")
public class SupplierController {


    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }


    /** <summary>
     * Supplier creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Suppliers</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createSupplier(@Validated @RequestBody SupplierRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierResponseDto response = service.createSupplier(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supplier update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Suppliers</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplier(@Validated @RequestBody  SupplierRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierResponseDto response = service.updateSupplier(request);
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
    public ResponseEntity<Response> getSupplier(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierResponseDto response = service.findSupplier(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Get all records endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting all records and its searchable</remarks>
     */
    @GetMapping("")
    public ResponseEntity<Response> getSuppliers(@RequestParam(value = "name",required = false)String name,
                                                 @RequestParam(value = "stateID",required = false)Long stateID,
                                                 @RequestParam(value = "address",required = false)String address,
                                                 @RequestParam(value = "phone",required = false)String phone,
                                                 @RequestParam(value = "email",required = false)String email,
                                                 @RequestParam(value = "website",required = false)String website,
                                                 @RequestParam(value = "supplierCategoryID",required = false)Long supplierCategoryID,
                                                 @RequestParam(value = "contactPerson",required = false)String contactPerson,
                                                 @RequestParam(value = "contactPhone",required = false)String contactPhone,
                                                 @RequestParam(value = "contactEmail",required = false)String contactEmail,
                                                 @RequestParam(value = "discountProvided",required = false)Double discountProvided,
                                                 @RequestParam(value = "page") int page,
                                                 @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode;
        Response resp = new Response();
        Page<Supplier> response = service.findAll(name, stateID, address, phone, email, website, supplierCategoryID, contactPerson,
                                                    contactPhone, contactEmail, discountProvided, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Suppliers</remarks>
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
        List<Supplier> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


}
