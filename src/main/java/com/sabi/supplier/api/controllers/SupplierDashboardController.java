package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierDashboardService;
import com.sabi.suppliers.core.dto.request.StockDto;
import com.sabi.suppliers.core.models.response.DashboardProductResponse;
import com.sabi.suppliers.core.models.response.DashboardWarehouseResponse;
import com.sabi.suppliers.core.models.response.StockResponseDto;
import com.sabi.suppliers.core.models.response.SupplierDashbaordResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"dashboard")
public class SupplierDashboardController {

    @Autowired
   private SupplierDashboardService dashboardService;

    @PostMapping("summary")
//    public ResponseEntity<Response> createDashboardInfo(@PathVariable Long supplierId){
    public ResponseEntity<Response> createDashboardInfo(@RequestParam(value = "supplierId") Long supplierId,
                                                        @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                        @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierDashbaordResponseDto response = dashboardService.createDashboardInfo(supplierId,startDate,endDate);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }

    @PostMapping("summary/product")
    public ResponseEntity<Response> getTopProduct(@RequestParam(value = "supplierId") Long supplierId,
                                                  @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                  @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate

    ){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<DashboardProductResponse> response = dashboardService.fetchTopProduct(supplierId,startDate,endDate);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }

//    @GetMapping("summary/warehouse/{productId}")
//    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
//    public ResponseEntity<Response> getWarehouse(@PathVariable Long productId){
//        HttpStatus httpCode ;
//        Response resp = new Response();
//        List<DashboardWarehouseResponse> response = dashboardService.fetchWarehouseProduct(productId);
//        resp.setCode(CustomResponseCode.SUCCESS);
//        resp.setDescription("Successful");
//        resp.setData(response);
//        httpCode = HttpStatus.CREATED;
//        return new ResponseEntity<>(resp, httpCode);
//    }

    @GetMapping("summary/warehouseDate")
    public ResponseEntity<Response> getWarehouseDate(@RequestParam(value = "productId") Long productId,
                                                     @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                     @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<DashboardWarehouseResponse> response = dashboardService.fetchWarehouseProductDate(productId,startDate,endDate);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }
}
