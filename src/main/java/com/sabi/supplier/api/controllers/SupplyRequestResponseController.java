package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.exceptions.BadRequestException;
import com.sabi.framework.helpers.ResponseHelper;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplyRequestResponseService;
import com.sabi.suppliers.core.dto.request.SupplyRequestResponseRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(Constants.APP_CONTENT + "supplyrequestresponse")
public class SupplyRequestResponseController {

    private final ResponseHelper responseHelper;
    private final SupplyRequestResponseService supplyRequestResponseService;

    public SupplyRequestResponseController(ResponseHelper responseHelper, SupplyRequestResponseService supplyRequestResponseService) {
        this.responseHelper = responseHelper;
        this.supplyRequestResponseService = supplyRequestResponseService;
    }

    @PostMapping
    public ResponseEntity<Response> createSupplyRequestResponse(@RequestBody @Valid SupplyRequestResponseRequest request) {
        return responseHelper.buildResponse(supplyRequestResponseService.createSupplyRequestResponse(request), HttpStatus.CREATED, "Successful");
    }

    @PutMapping
    public ResponseEntity<Response> updateSupplyRequestResponse(@RequestBody @Valid SupplyRequestResponseRequest request) {
        if (request.getId() == null || request.getId() < 0)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Id Can not be empty");
        return responseHelper.buildResponse(supplyRequestResponseService.updateSupplyRequestResponse(request), HttpStatus.OK, "Update Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSupplyRequestResponseById(@PathVariable long id) {
        return responseHelper.buildResponse(supplyRequestResponseService.findsupplyRequestResponse(id), HttpStatus.OK, "Record fetched successfully !");
    }

    @GetMapping
    public ResponseEntity<Response> getSupplyRequestResponses(@RequestParam(value = "supplyRequestId", required = false) Long supplyRequestId,
                                                              @RequestParam(value = "page") int page,
                                                              @RequestParam(value = "pageSize") int pageSize) {
        return responseHelper
                .buildResponse(supplyRequestResponseService.findSupplyRequestResponses(supplyRequestId, PageRequest.of(page, pageSize)),
                        HttpStatus.OK, "Record fetched successfully !");
    }

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@RequestBody EnableDisEnableDto request) {
        supplyRequestResponseService.enableDisEnableState(request);
        return responseHelper.buildEnableDisable();
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive") Boolean isActive) {
        return responseHelper.buildResponse(supplyRequestResponseService.getAll(isActive), HttpStatus.OK, "Record fetched successfully !");
    }
}
