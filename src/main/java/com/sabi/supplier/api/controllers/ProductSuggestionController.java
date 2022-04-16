package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.ProductService;
import com.sabi.supplier.service.services.ProductSuggestionService;
import com.sabi.suppliers.core.dto.request.ProductDto;
import com.sabi.suppliers.core.dto.request.ProductSuggestionRequestDto;
import com.sabi.suppliers.core.models.Product;
import com.sabi.suppliers.core.models.ProductSuggestion;
import com.sabi.suppliers.core.models.response.ProductResponseDto;
import com.sabi.suppliers.core.models.response.ProductSuggestionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.APP_CONTENT+"productSuggestion")
public class ProductSuggestionController {

    @Autowired
    private ProductSuggestionService service;


    /** <summary>
     * Product suggestion suggestion creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Product suggestion</remarks>
     */

    @PostMapping("")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> createProductSuggestion(@Validated @RequestBody ProductSuggestionRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductSuggestionResponseDto response = service.createProductSuggestion(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Product suggestion update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Product suggestion</remarks>
     */

    @PutMapping("")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> updateProductSuggestion(@Validated @RequestBody ProductSuggestionRequestDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductSuggestionResponseDto response = service.updateProductSuggestion(request);
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
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> getProductSuggestionById(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        ProductSuggestionResponseDto response = service.findProductSuggestion(id);
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
    public ResponseEntity<Response> getProductSuggestions(@RequestParam(value = "name",required = false)String name,
                                               @RequestParam(value = "manufacturer",required = false)String manufacturer,
                                               @RequestParam(value = "status",required = false)String status,
                                               @RequestParam(value = "page") int page,
                                               @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<ProductSuggestion> response = service.findAllProductSuggestions(name,manufacturer,status, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disenable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disenabling a Product suggestion</remarks>
     */

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisEnableState(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
