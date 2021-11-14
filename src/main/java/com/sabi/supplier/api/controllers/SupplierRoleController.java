package com.sabi.supplier.api.controllers;


import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierRoleService;
import com.sabi.suppliers.core.dto.request.SupplierRoleDto;
import com.sabi.suppliers.core.dto.response.SupplierRoleResponseDto;
import com.sabi.suppliers.core.models.SupplierRole;
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
@RequestMapping(Constants.APP_CONTENT+"supplierrole")
public class SupplierRoleController {

    @Autowired
    private SupplierRoleService service;



    /** <summary>
     * Supplier Role creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Supplier role</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createSupplierUser(@Validated @RequestBody SupplierRoleDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierRoleResponseDto response = service.createSupplierRole(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Supplier role update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Supplier role</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateSupplierUser(@Validated @RequestBody  SupplierRoleDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierRoleResponseDto response = service.updateSupplierRole(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Get single record endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting a single role</remarks>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSupplierUser(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierRoleResponseDto response = service.findSupplierRole(id);
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
    public ResponseEntity<Response> getSupplierUser(@RequestParam(value = "name",required = false)String name,
                                                    @RequestParam(value = "partnerId",required = false)Long partnerId,
                                                    @RequestParam(value = "roleId",required = false)Long roleId,
                                                    @RequestParam(value = "page") int page,
                                                    @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<SupplierRole> response = service.findAll(name, partnerId,roleId, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Supplier user</remarks>
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
        List<SupplierRole> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
