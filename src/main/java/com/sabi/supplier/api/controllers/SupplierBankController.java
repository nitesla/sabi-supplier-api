package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.exceptions.BadRequestException;
import com.sabi.framework.helpers.ResponseHelper;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierBankService;
import com.sabi.supplier.service.services.SupplierBankService;
import com.sabi.suppliers.core.dto.request.SupplierBankRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.APP_CONTENT+"supplierbank")
public class SupplierBankController {
    private final ResponseHelper responseHelper;
    private final SupplierBankService supplierBankService;

    public SupplierBankController(ResponseHelper responseHelper, SupplierBankService supplierBankService) {
        this.responseHelper = responseHelper;
        this.supplierBankService = supplierBankService;
    }

    @PostMapping
    public ResponseEntity<Response> createsupplierBank(@RequestBody @Valid SupplierBankRequest request) {
        return responseHelper.buildResponse(supplierBankService.createsupplierBank(request), HttpStatus.CREATED, "Successful");
    }

    @PutMapping
    public ResponseEntity<Response> updatesupplierBank(@RequestBody @Valid SupplierBankRequest request) {
        if (request.getId() == null || request.getId() < 0)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Id Can not be empty");
        return responseHelper.buildResponse(supplierBankService.updatesupplierBank(request), HttpStatus.OK, "Update Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getsupplierBankById(@PathVariable long id) {
        return responseHelper.buildResponse(supplierBankService.findsupplierBank(id), HttpStatus.OK, "Record fetched successfully !");
    }

    @GetMapping
    public ResponseEntity<Response> getsupplierBanks(@RequestParam(value = "accountNumber", required = false) String accountNumber,
                                                     @RequestParam(value = "supplierId", required = false) Long supplierId,
                                                              @RequestParam(value = "page") int page,
                                                              @RequestParam(value = "pageSize") int pageSize) {
        return responseHelper
                .buildResponse(supplierBankService.findAll(accountNumber, supplierId,PageRequest.of(page, pageSize)),
                        HttpStatus.OK, "Record fetched successfully !");
    }

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@RequestBody EnableDisEnableDto request) {
        supplierBankService.enableDisEnablesupplierBank(request);
        return responseHelper.buildEnableDisable();
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive") Boolean isActive) {
        return responseHelper.buildResponse(supplierBankService.getAll(isActive), HttpStatus.OK, "Record fetched successfully !");
    }
}
