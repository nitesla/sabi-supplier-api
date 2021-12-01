package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.exceptions.BadRequestException;
import com.sabi.framework.helpers.ResponseHelper;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.WareHouseUserService;
import com.sabi.suppliers.core.dto.request.WareHouseUserRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"warehouseuser")
public class WareHouseUserController {

    private final ResponseHelper responseHelper;
    private final WareHouseUserService wareHouseService;

    public WareHouseUserController(ResponseHelper responseHelper, WareHouseUserService wareHouseService) {
        this.responseHelper = responseHelper;
        this.wareHouseService = wareHouseService;
    }

    @PostMapping
    public ResponseEntity<Response> createWareHouse(@RequestBody @Valid WareHouseUserRequest request){
        return responseHelper.buildResponse( wareHouseService.createWareHouseUser(request), HttpStatus.CREATED, "Successful");
    }

    @PutMapping
    public ResponseEntity<Response> updateWareHouseUser(@RequestBody @Valid WareHouseUserRequest request){
        if(request.getId() == null || request.getId() < 0)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Id Can not be empty");
        return responseHelper.buildResponse(wareHouseService.updateWareHouseUser(request), HttpStatus.OK, "Update Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getWarehouseUserById(@PathVariable long id){
        return responseHelper.buildResponse(wareHouseService.findWareHouseUser(id), HttpStatus.OK, "Record fetched successfully !");
    }

    @GetMapping
    public ResponseEntity<Response> getWarehouseUsers(@RequestParam(value = "userId",required = false)Long userId,
                                                 @RequestParam(value = "page") int page,
                                                 @RequestParam(value = "pageSize") int pageSize){
        return responseHelper
                .buildResponse(wareHouseService.findWareHouseUsers(userId, PageRequest.of(page, pageSize)),
                        HttpStatus.OK, "Record fetched successfully !");
    }

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@RequestBody EnableDisEnableDto request){
        wareHouseService.enableDisEnableState(request);
        return responseHelper.buildEnableDisable();
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        return responseHelper.buildResponse(wareHouseService.getAll(isActive), HttpStatus.OK, "Record fetched successfully !");
    }
}
