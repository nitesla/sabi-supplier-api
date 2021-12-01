package com.sabi.supplier.api.controllers;

import com.sabi.framework.utils.Constants;
import com.sabi.supplier.service.services.SupplierUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"supplieruser")
public class SupplierUserController {

    @Autowired
    private SupplierUserService service;




}
