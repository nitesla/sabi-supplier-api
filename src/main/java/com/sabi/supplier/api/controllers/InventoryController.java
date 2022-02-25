package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.InventoryService;
import com.sabi.supplier.service.services.WareHouseService;
import com.sabi.supplier.service.services.WareHouseUserService;
import com.sabi.suppliers.core.dto.request.InventoryDto;
import com.sabi.suppliers.core.dto.request.WareHouseRequest;
import com.sabi.suppliers.core.models.Inventory;
import com.sabi.suppliers.core.models.response.InventoryResponseDto;
import com.sabi.suppliers.core.models.response.WareHouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @Autowired
    private WareHouseService wareHouseService;

    /** <summary>
     * inventory creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new inventory</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createInventory(@Validated @RequestBody InventoryDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        InventoryResponseDto response = service.createInventory(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * inventory update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating inventory</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateInventory(@Validated @RequestBody InventoryDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        InventoryResponseDto response = service.updateInventory(request);
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
    public ResponseEntity<Response> getInventory(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        InventoryResponseDto response = service.findInventoryById(id);
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
    public ResponseEntity<Response> getInventories(@RequestParam(value = "supplierRequestId",required = false)Long supplierRequestId,
                                                 @RequestParam(value = "warehouseId",required = false)Long warehouseId,
                                                 @RequestParam(value = "page") int page,
                                                 @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<Inventory> response = service.findAll(supplierRequestId,warehouseId, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disenable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disenabling a inventory</remarks>
     */

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisEnableInventory(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Inventory> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

//    @GetMapping("warehouseId/{warehouseId}")
//    public ResponseEntity<Response> getInventoryByWarehouseID(PathVariable Long warehouseId){
//        HttpStatus httpCode ;
//        Response resp = new Response();
//        List<Inventory> response = service.getInventoryiesByWarehouseId(warehouseId);
//        resp.setCode(CustomResponseCode.SUCCESS);
//        resp.setDescription("Record fetched successfully !");
//        resp.setData(response);
//        httpCode = HttpStatus.OK;
//        return new ResponseEntity<>(resp, httpCode);
//    }

    @GetMapping("warehouseId/{warehouseId}")
    public ResponseEntity<Response> getInventoryByWarehouseID(@PathVariable Long warehouseId){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Inventory> response = service.getInventoryiesByWarehouseId(warehouseId);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @PutMapping("warehouse")
    public ResponseEntity<Response> updateInventoryWarehouse(@Validated @RequestBody WareHouseRequest request){
        HttpStatus httpCode ;
        Response resp = new Response();
        WareHouseResponse response = wareHouseService.updateWareHouse(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
