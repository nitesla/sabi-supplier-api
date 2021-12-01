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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Response> supplierSignUp(@Validated @RequestBody SupplierSignUpRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierSignUpResponse response = service.supplierSignUp(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }


    @PutMapping("/completesignup")
    public ResponseEntity<Response> completeSignUp(@Validated @RequestBody CompleteSignUpDto request){
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
    public ResponseEntity<Response> updateSupplier(@Validated @RequestBody  SupplierRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierResponseDto response = service.updateSupplier(request);
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






    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Suppliers</remarks>
     */

    @PutMapping("/enabledisable")
    public ResponseEntity<Response> enableDisable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisable(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }





}
