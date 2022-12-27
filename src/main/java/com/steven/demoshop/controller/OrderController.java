package com.steven.demoshop.controller;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.dto.order.*;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.model.OrderMaster;
import com.steven.demoshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/demoshop")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/members/{memberId}/orders")
    public ResponseEntity<?> createOrder(
            @PathVariable @Min(1) Integer memberId,
            @RequestBody @Valid CreateOrderRequest createOrderRequest
    ) {
        List<OrderDetailParam> details = createOrderRequest.getDetails();
        int totalPrice = 0;
        for (OrderDetailParam detail : details) {
            totalPrice += detail.getOdPrice();
        }

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailParam detail : details) {
            OrderDetail od = OrderDetail.builder().productId(detail.getProductId()).quantity(detail.getQuantity()).odPrice(detail.getOdPrice()).build();
            orderDetails.add(od);
        }

        OrderMaster orderMaster = OrderMaster.builder().memberId(memberId).storeId(createOrderRequest.getStoreId()).orderDetails(orderDetails).status(OrderStatus.CREATED).totalPrice(totalPrice).build();

        Integer orderId = orderService.createOrder(orderMaster);
        OrderMaster master = orderService.getOrder(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(master);
    }

    @GetMapping("/members/{memberId}/orders")
    public ResponseEntity<List<OrderMaster>> getMemberOrders(
            @PathVariable @Min(1) Integer memberId,
            @RequestParam(defaultValue = "CREATED") OrderStatus orderStatus,
            @RequestParam(defaultValue = "create_time") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "1") Integer page
    ) {

        OrderQueryParam queryParam = OrderQueryParam.builder()
                .memberId(memberId)
                .status(orderStatus)
                .orderBy("create_time")
                .sort("desc")
                .page(page)
                .build();

        List<OrderMaster> masters = orderService.getOrderList(queryParam);


        return ResponseEntity.status(HttpStatus.OK).body(masters);
    }

    @GetMapping("/stores/{storeId}/orders")
    public ResponseEntity<List<OrderMaster>> getStoreOrders(
            @PathVariable @Min(1) Integer storeId,
            @RequestParam(defaultValue = "CREATED") OrderStatus orderStatus,
            @RequestParam(defaultValue = "create_time") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        OrderQueryParam queryParam = OrderQueryParam.builder()
                .storeId(storeId)
                .status(orderStatus)
                .orderBy(orderBy)
                .sort(sort)
                .page(page)
                .build();

        List<OrderMaster> masters = orderService.getOrderList(queryParam);

        return ResponseEntity.status(HttpStatus.OK).body(masters);
    }
}
