package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierDashboardService;
import com.sabi.suppliers.core.dto.request.StockDto;
import com.sabi.suppliers.core.models.response.StockResponseDto;
import com.sabi.suppliers.core.models.response.SupplierDashbaordResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"dashboard")
public class SupplierDashboardController {

    @Autowired
   private SupplierDashboardService dashboardService;

    @PostMapping("summary/{supplierId}")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> createDashboardInfo(@PathVariable Long supplierId){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierDashbaordResponseDto response = dashboardService.createDashboardInfo(supplierId);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }
}
