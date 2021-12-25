package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.ChangePasswordDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.models.User;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.SupplierUserService;
import com.sabi.suppliers.core.dto.request.SupplierUserActivation;
import com.sabi.suppliers.core.dto.request.SupplierUserDto;
import com.sabi.suppliers.core.dto.response.SupplierActivationResponse;
import com.sabi.suppliers.core.dto.response.SupplierUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"supplieruser")
public class SupplierUserController {



    private final SupplierUserService service;



    public SupplierUserController(SupplierUserService service) {
        this.service = service;

    }



    @PostMapping("")
    public ResponseEntity<Response> createSupplierUser(@Validated @RequestBody SupplierUserDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierUserResponseDto response = service.createSupplierUser(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    @PostMapping("/activatesupplieruser")
    public ResponseEntity<Response> activateSupplierUser(@Validated @RequestBody SupplierUserActivation request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.activateSupplierUser(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }





    @PutMapping("/passwordactivation")
    public ResponseEntity<Response> partnerPasswordActivation(@Validated @RequestBody ChangePasswordDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        SupplierActivationResponse response = service.supplierPasswordActivation(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Password changed successfully");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/page")
    public ResponseEntity<Response> getSupplierUser(@RequestParam(value = "firstName",required = false)String firstName,
                                                   @RequestParam(value = "phone",required = false)String phone,
                                                   @RequestParam(value = "email",required = false)String email,
                                                   @RequestParam(value = "username",required = false)String username,
                                                   @RequestParam(value = "roleId",required = false)Long roleId,
                                                   @RequestParam(value = "isActive",required = false)Boolean isActive,
                                                   @RequestParam(value = "lastName",required = false)String lastName,
                                                   @RequestParam(value = "page") int page,
                                                   @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();

            Page<User> response = service.findByClientId(firstName, phone, email, username, roleId, lastName, PageRequest.of(page, pageSize));
            resp.setCode(CustomResponseCode.SUCCESS);
            resp.setDescription("Record fetched successfully !");
            resp.setData(response);
            httpCode = HttpStatus.OK;
            return new ResponseEntity<>(resp, httpCode);
        }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive",required = false)Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();

            List<User> response = service.getAll(isActive);
            resp.setCode(CustomResponseCode.SUCCESS);
            resp.setDescription("Record fetched successfully !");
            resp.setData(response);
            httpCode = HttpStatus.OK;
            return new ResponseEntity<>(resp, httpCode);
    }

}
