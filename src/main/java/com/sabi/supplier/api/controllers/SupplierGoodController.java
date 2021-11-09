package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierGoodService;
import com.sabi.suppliers.core.dto.request.SupplierGoodDto;
import com.sabi.suppliers.core.dto.response.SupplierGoodResponseDto;
import com.sabi.suppliers.core.models.SupplierGood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"suppliergoods")
public class SupplierGoodController {

    private final SupplierGoodService service;

    public SupplierGoodController(SupplierGoodService service) {
        this.service = service;
    }

    /** <summary>
     * Supplier Good creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Supplier Location</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createSupplierLocation(@Validated @RequestBody SupplierGoodDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierGoodResponseDto response = service.createSupplierGood(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supplier Good update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Supplier Location</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplierLocation(@Validated @RequestBody  SupplierGoodDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierGoodResponseDto response = service.updateSupplierGood(request);
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
        SupplierGoodResponseDto response = service.findSupplierGood(id);
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
    public ResponseEntity<Response> getSupplierLocations(@RequestParam(value = "supplierProductId",required = false)Long supplierProductId,
                                                         @RequestParam(value = "statvariantIdeID",required = false)Long variantId,
                                                         @RequestParam(value = "price",required = false)Long price,
                                                         @RequestParam(value = "page") int page,
                                                         @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<SupplierGood> response = service.findAll(supplierProductId, variantId, price, PageRequest.of(page, pageSize));
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
        service.enableDisEnable(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<SupplierGood> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
