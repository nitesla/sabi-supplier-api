package com.sabi.supplier.api.controllers;

import com.sabi.framework.dto.requestDto.EnableDisEnableDto;
import com.sabi.framework.dto.responseDto.Response;
import com.sabi.framework.utils.Constants;
import com.sabi.framework.utils.CustomResponseCode;
import com.sabi.supplier.service.services.ShipmentService;
import com.sabi.suppliers.core.dto.request.ShipmentDto;
import com.sabi.suppliers.core.dto.request.ShipmentShipmentItemDto;
import com.sabi.suppliers.core.models.Shipment;
import com.sabi.suppliers.core.models.response.ShipmentResponseDto;
import com.sabi.suppliers.core.models.response.ShipmentShipmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"shipment")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    /** <summary>
     * shipment creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new shipment</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createShipment(@Validated @RequestBody ShipmentDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ShipmentResponseDto response = service.createShipment(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }

    @PostMapping("shipmentItem")
    public ResponseEntity<Response> createShipments(@Validated @RequestBody ShipmentShipmentItemDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ShipmentShipmentResponseDto response = service.createShipmentItems(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }





    /** <summary>
     * shipment update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating shipment</remarks>
     */

    @PutMapping("")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> updateShipment(@Validated @RequestBody ShipmentDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ShipmentResponseDto response = service.updateShipment(request);
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
    public ResponseEntity<Response> getShipment(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        ShipmentResponseDto response = service.findShipment(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("/masterShipment/{id}")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> getMasterShipment(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        ShipmentShipmentResponseDto response = service.findMasterShipment(id);
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
    public ResponseEntity<Response> getShipment(@RequestParam(value = "warehouseId",required = false)Long warehouseId,
                                              @RequestParam(value = "logisticPartnerId",required = false)Long logisticPartnerId,
                                              @RequestParam(value = "logisticsPartnerName",required = false)String logisticsPartnerName,
                                              @RequestParam(value = "phoneNumber",required = false)String phoneNumber,
                                              @RequestParam(value = "vehicle",required = false)String vehicle,
                                              @RequestParam(value = "status",required = false)String status,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "pageSize") int pageSize){
            HttpStatus httpCode ;
        Response resp = new Response();
        Page<Shipment> response = service.findAll(warehouseId,logisticPartnerId,logisticsPartnerName,phoneNumber,vehicle,status, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Enable disenable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disenabling a Shipment</remarks>
     */

    @PutMapping("/enabledisenable")
    public ResponseEntity<Response> enableDisEnable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisEnableShipment(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "isActive")Boolean isActive){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Shipment> response = service.getAll(isActive);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("/list/supplierId")
    public ResponseEntity<Response> getAllShipment(@RequestParam(value = "supplierId")Long supplierId){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Shipment> response = service.getAllShipment(supplierId);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

//    @GetMapping("/list/supplierId/data")
//    public ResponseEntity<Response> getAllShipmentData(@RequestParam(value = "supplierId")Long supplierId,
//                                                       @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                                                       @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
//        HttpStatus httpCode ;
//        Response resp = new Response();
//        List<Shipment> response = service.getAllShipmentData(supplierId,startDate,endDate);
//        resp.setCode(CustomResponseCode.SUCCESS);
//        resp.setDescription("Record fetched successfully !");
//        resp.setData(response);
//        httpCode = HttpStatus.OK;
//        return new ResponseEntity<>(resp, httpCode);
//    }
}
