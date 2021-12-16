package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.exceptions.BadRequestException;
import com.sabi.framework.helpers.ResponseHelper;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplyRequestService;
import com.sabi.suppliers.core.dto.request.SupplyRequestRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;


@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT + "supplyrequest")
public class SupplyRequestController {

    private final ResponseHelper responseHelper;
    private final SupplyRequestService supplyRequestService;

    public SupplyRequestController(ResponseHelper responseHelper, SupplyRequestService supplyRequestService) {
        this.responseHelper = responseHelper;
        this.supplyRequestService = supplyRequestService;
    }

    @PostMapping
    public ResponseEntity<Response> createSupplyRequest(@RequestBody @Valid SupplyRequestRequest request) {
        return responseHelper.buildResponse(supplyRequestService.createSupplyRequest(request), HttpStatus.CREATED, "Successful");
    }

    @PutMapping
    public ResponseEntity<Response> updateSupplyRequest(@RequestBody @Valid SupplyRequestRequest request) {
        if (request.getId() == null || request.getId() < 0)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Id Can not be empty");
        return responseHelper.buildResponse(supplyRequestService.updateSupplyRequest(request), HttpStatus.OK, "Update Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSupplyRequestById(@PathVariable long id) {
        return responseHelper.buildResponse(supplyRequestService.findSupplyRequest(id), HttpStatus.OK, "Record fetched successfully !");
    }

    @GetMapping
    public ResponseEntity<Response> getSupplyRequests(@RequestParam(value = "productId", required = false) Long productId,
                                                      @RequestParam(value = "productName", required = false) String productName,
                                                      @RequestParam(value = "askingQuantity", required = false) Long askingQuantity,
                                                      @RequestParam(value = "askingPrice", required = false) BigDecimal askingPrice,
                                                      @RequestParam(value = "startTime", required = false) Date startTime,
                                                      @RequestParam(value = "endTime", required = false) Date endTime,
                                                      @RequestParam(value = "referenceNo", required = false) String referenceNo,
                                                      @RequestParam(value = "status", required = false) String status,
                                                      @RequestParam(value = "warehouseId", required = false) Long warehouseId,
                                                      @RequestParam(value = "supplierId", required = false) Long supplierId,
                                                      @RequestParam(value = "page") int page,
                                                      @RequestParam(value = "pageSize") int pageSize) throws ParseException {
        return responseHelper
                .buildResponse(supplyRequestService.findAll(productId, productName, askingQuantity,
                                askingPrice, startTime, endTime, referenceNo, status,warehouseId,supplierId, PageRequest.of(page, pageSize)),
                        HttpStatus.OK, "Record fetched successfully !");
    }

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@RequestBody EnableDisEnableDto request) {
        supplyRequestService.enableDisEnableState(request);
        return responseHelper.buildEnableDisable();
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive") Boolean isActive) {
        return responseHelper.buildResponse(supplyRequestService.getAll(isActive), HttpStatus.OK, "Record fetched successfully !");
    }
}
