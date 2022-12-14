package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.AssetTypeService;
import com.sabi.supplier.service.services.SupplierService;
import com.sabi.suppliers.core.dto.request.AssetTypeRequest;
import com.sabi.suppliers.core.dto.request.CompleteSignUpDto;
import com.sabi.suppliers.core.dto.request.SupplierRequestDto;
import com.sabi.suppliers.core.dto.request.SupplierSignUpRequestDto;
import com.sabi.suppliers.core.dto.response.AssetTypeResponse;
import com.sabi.suppliers.core.dto.response.CompleteSignUpResponse;
import com.sabi.suppliers.core.dto.response.SupplierResponseDto;
import com.sabi.suppliers.core.dto.response.SupplierSignUpResponse;
import com.sabi.suppliers.core.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"supplier")
public class SupplierController {


    private final SupplierService service;
    private final AssetTypeService assetTypeService;

    public SupplierController(SupplierService service,AssetTypeService assetTypeService) {
        this.service = service;
        this.assetTypeService = assetTypeService;
    }




    @PostMapping("/signup")
    public ResponseEntity<Response> supplierSignUp(@Validated @RequestBody SupplierSignUpRequestDto request,HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierSignUpResponse response = service.supplierSignUp(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }


    @PutMapping("/completesignup")
    public ResponseEntity<Response> completeSignUp(@Validated @RequestBody CompleteSignUpDto request) throws IOException {
        HttpStatus httpCode ;
        Response resp = new Response();
        CompleteSignUpResponse response = service.completeSignUp(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    @GetMapping("/assetType")
    public AssetTypeResponse assetTypes (AssetTypeRequest request) throws Exception {
        AssetTypeResponse response= assetTypeService.assetTypes(request);
        return response;
    }





    /** <summary>
     * Supplier update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Suppliers</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplier(@Validated @RequestBody  SupplierRequestDto request,HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierResponseDto response = service.updateSupplier(request,request1);
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
    public ResponseEntity<Response> getSupplier(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierResponseDto response = service.findSupplier(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    @GetMapping("")
    public ResponseEntity<Response> getSuppliers(@RequestParam(value = "name",required = false)String name,
                                                 @RequestParam(value = "isActive",required = false)boolean isActive,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<Supplier> response = service.findAll(name,isActive,PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Supplier> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }




    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Suppliers</remarks>
     */

    @PutMapping("/enabledisable")
    public ResponseEntity<Response> enableDisable(@Validated @RequestBody EnableDisEnableDto request,HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisable(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }





}
