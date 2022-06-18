package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.helpers.ResponseHelper;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplyRequestCounterOfferService;
import com.sabi.suppliers.core.dto.request.SupplyRequestCounterOfferRequestDto;
import com.sabi.suppliers.core.dto.response.SupplyRequestCounterOfferResponseDto;
import com.sabi.suppliers.core.models.SupplyRequestCounterOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(Constants.APP_CONTENT + "supplyrequestCounterOffer")
public class SupplyRequestCounterOfferController {

    @Autowired
    private SupplyRequestCounterOfferService service;
    private final ResponseHelper responseHelper;

    public SupplyRequestCounterOfferController(SupplyRequestCounterOfferService service, ResponseHelper responseHelper) {
        this.service = service;
        this.responseHelper = responseHelper;
    }

    @PostMapping("")
    public ResponseEntity<Response> createSupplierRequestCounterOffer(@Validated @RequestBody SupplyRequestCounterOfferRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplyRequestCounterOfferResponseDto response = service.createSupplyRequestCounterOffer(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supply Request Counter Offer update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Supply Request Counter Offer</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplierRequestCounterOffer(@Validated @RequestBody  SupplyRequestCounterOfferRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplyRequestCounterOfferResponseDto response = service.updateSupplyRequestCounterOffer(request);
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
    public ResponseEntity<Response> getSupplierRequestCounterOffer(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplyRequestCounterOfferResponseDto response = service.findSupplyRequestCounterOfferById(id);
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
    public ResponseEntity<Response> getSupplierRequestCounterOffers(@RequestParam(value = "price",required = false) BigDecimal price,
                                                          @RequestParam(value = "supplierRequestId",required = false)Long supplierRequestId,
                                                          @RequestParam(value = "quantity",required = false)Integer quantity,
                                                          @RequestParam(value = "userId",required = false)Long userId,
                                                          @RequestParam(value = "page") int page,
                                                          @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<SupplyRequestCounterOffer> response = service.findAllSupplyRequestCounterOffer(price, supplierRequestId, quantity,userId, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Supplier Request Counter Offer</remarks>
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
        List<SupplyRequestCounterOffer> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
