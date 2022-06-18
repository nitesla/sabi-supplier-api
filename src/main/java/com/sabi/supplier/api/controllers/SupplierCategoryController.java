package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierCategoryService;
import com.sabi.suppliers.core.dto.request.SupplierCategoryRequestDto;
import com.sabi.suppliers.core.dto.response.SupplierCategoryResponseDto;
import com.sabi.suppliers.core.models.SupplierCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"suppliercategory")
public class SupplierCategoryController {


    private final SupplierCategoryService service;

    public SupplierCategoryController(SupplierCategoryService service) {
        this.service = service;
    }


    /** <summary>
     * Supplier Category creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Supplier Category</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createSupplierCategory(@Validated @RequestBody SupplierCategoryRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierCategoryResponseDto response = service.createSupplierCategory(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supplier Category update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Supplier Categories</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplierCategory(@Validated @RequestBody  SupplierCategoryRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierCategoryResponseDto response = service.updateSupplierCategory(request);
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
    public ResponseEntity<Response> getSupplierCategory(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierCategoryResponseDto response = service.findSupplierCategory(id);
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
    public ResponseEntity<Response> getSupplierCategories(@RequestParam(value = "name",required = false)String name,
                                                          @RequestParam(value = "creditPeriod",required = false)Integer creditPeriod,
                                                          @RequestParam(value = "isActive",required = false)Boolean isActive,
                                                          @RequestParam(value = "page") int page,
                                                          @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<SupplierCategory> response = service.findAll(name, creditPeriod, isActive, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Supplier Category</remarks>
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
        List<SupplierCategory> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


}
