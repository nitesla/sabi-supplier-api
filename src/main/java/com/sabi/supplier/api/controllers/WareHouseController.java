package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.exceptions.BadRequestException;
import com.sabi.framework.helpers.ResponseHelper;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.WareHouseService;
import com.sabi.suppliers.core.dto.request.DefaultWarehouseRequest;
import com.sabi.suppliers.core.dto.request.WareHouseRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT + "warehouse")
public class WareHouseController {
    private final ResponseHelper responseHelper;
    private final WareHouseService wareHouseService;

    public WareHouseController(ResponseHelper responseHelper, WareHouseService wareHouseService) {
        this.responseHelper = responseHelper;
        this.wareHouseService = wareHouseService;
    }

    @PostMapping
    public ResponseEntity<Response> createWareHouse(@RequestBody @Valid WareHouseRequest request) {
        return responseHelper.buildResponse(wareHouseService.createWareHouse(request), HttpStatus.CREATED, "Successful");
    }

    @PutMapping
    public ResponseEntity<Response> updateWareHouse(@RequestBody @Valid WareHouseRequest request) {
        if (request.getId() == null || request.getId() < 0)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Id Can not be empty");
        return responseHelper.buildResponse(wareHouseService.updateWareHouse(request), HttpStatus.OK, "Update Successful");
    }

    @PutMapping("isDefault")
    public ResponseEntity<Response> warehouseIdDefault(@RequestBody @Valid DefaultWarehouseRequest request) {
        if (request.getId() == null || request.getId() < 0)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Id Can not be empty");
        return responseHelper.buildResponse(wareHouseService.setWareHouseAsDefault(request), HttpStatus.OK, "Update Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getWarehouseById(@PathVariable long id) {
        return responseHelper.buildResponse(wareHouseService.findWareHouse(id), HttpStatus.OK, "Record fetched successfully !");
    }

    @GetMapping
    public ResponseEntity<Response> getWarehouses(@RequestParam(value = "productId", required = false) Long productId,
                                                  @RequestParam(value = "supplierId", required = false) Long supplierId,
                                                  @RequestParam(value = "stateId", required = false) Long stateId,
                                                  @RequestParam(value = "address", required = false) String address,
                                                  @RequestParam(value = "contactPerson", required = false) String contactPerson,
                                                  @RequestParam(value = "contactPhone", required = false) String contactPhone,
                                                  @RequestParam(value = "contactEmail", required = false) String contactEmail,
                                                  @RequestParam(value = "longitude", required = false) String longitude,
                                                  @RequestParam(value = "latitude", required = false) String latitude,
                                                  @RequestParam(value = "userId", required = false) Long userId,
                                                  @RequestParam(value = "lgaId", required = false) Long lgaId,
                                                  @RequestParam(value = "productCount", required = false) Long productCount,
                                                  @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "isActive", required = false) Boolean isActive,
                                                  @RequestParam(value = "page") int page,
                                                  @RequestParam(value = "pageSize") int pageSize) {
        return responseHelper
                .buildResponse(wareHouseService.findWareHouses(productId, supplierId, stateId, address, contactPerson,
                                contactPhone, contactEmail, longitude, latitude, userId, lgaId,
                                productCount, name, isActive, PageRequest.of(page, pageSize)),
                        HttpStatus.OK, "Record fetched successfully !");
    }

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@RequestBody EnableDisEnableDto request) {
        wareHouseService.enableDisEnableState(request);
        return responseHelper.buildEnableDisable();
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive") Boolean isActive,
                                           @RequestParam(value = "supplierId")Long supplierId) {
        return responseHelper.buildResponse(wareHouseService.getAll(isActive,supplierId), HttpStatus.OK, "Record fetched successfully !");
    }
}
