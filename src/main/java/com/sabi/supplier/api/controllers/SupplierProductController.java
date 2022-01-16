package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierProductService;
import com.sabi.suppliers.core.dto.request.SupplierProductDto;
import com.sabi.suppliers.core.dto.response.SupplierProductResponseDto;
import com.sabi.suppliers.core.models.SupplierProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"supplierproduct")
public class SupplierProductController {

    private final SupplierProductService service;

    public SupplierProductController(SupplierProductService service) {
        this.service = service;
    }

    /** <summary>
     * Supplier Product creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Supplier Product</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createSupplierProduct(@Validated @RequestBody SupplierProductDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierProductResponseDto response = service.createSupplierProduct(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supplier Product update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Supplier Product</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplierProduct(@Validated @RequestBody  SupplierProductDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierProductResponseDto response = service.updateSupplierProduct(request);
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
    public ResponseEntity<Response> getSupplierProduct(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierProductResponseDto response = service.findSupplierProduct(id);
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
    public ResponseEntity<Response> getSupplierProduct(@RequestParam(value = "supplierId",required = false)Long supplierId,
                                                         @RequestParam(value = "productId",required = false)Long productId,
                                                         @RequestParam(value = "page") int page,
                                                         @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<SupplierProduct> response = service.findAll(supplierId, productId,PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Supplier Product</remarks>
     */

    @PutMapping("/enabledisable")
    public ResponseEntity<Response> enableDisable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisEnable(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<SupplierProduct> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
