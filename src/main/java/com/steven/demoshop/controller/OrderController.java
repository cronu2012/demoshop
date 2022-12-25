package com.steven.demoshop.controller;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.dto.order.OrderAdd;
import com.steven.demoshop.dto.order.OrderAddVO;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.model.OrderMaster;
import com.steven.demoshop.service.OrderDetailService;
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

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/orders")
    public ResponseEntity<OrderAddVO> createOrder(@RequestBody @Valid OrderAdd orderAdd) {
        Integer orderId = orderService.createOrder(orderAdd);

        List<Map<String, Integer>> map = orderAdd.getDetail();

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Map<String, Integer> m : map) {
            OrderDetail od = OrderDetail.builder().orderId(orderId).productId(m.get("productId")).quantity(m.get("quantity")).odPrice(m.get("price")).build();
            orderDetails.add(od);
        }
        orderDetailService.addOrderDetail(orderDetails);

        OrderDetailQueryParam queryParam = OrderDetailQueryParam.builder().orderId(orderId).orderBy("create_time").sort("desc").build();


        List<OrderDetail> detailList = orderDetailService.getOrderDetail(queryParam);


        OrderMaster orderMaster = orderService.getOrder(orderId);

        OrderAddVO orderAddVO = new OrderAddVO();
        orderAddVO.setOrderId(orderMaster.getOrderId());
        orderAddVO.setMemberId(orderMaster.getMemberId());
        orderAddVO.setStoreId(orderMaster.getStoreId());
        orderAddVO.setStatus(orderMaster.getStatus());
        orderAddVO.setDetails(detailList);
        orderAddVO.setTotalPrice(orderMaster.getTotalPrice());
        orderAddVO.setCreateTime(orderMaster.getCreateTime());
        orderAddVO.setModifyTime(orderMaster.getModifyTime());



        return ResponseEntity.status(HttpStatus.OK).body(orderAddVO);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable @Min(1) Integer id) {
        OrderMaster orderMaster = orderService.getOrder(id);

        OrderDetailQueryParam queryParam = OrderDetailQueryParam.builder().orderId(id).orderBy("create_time").sort("asc").build();

        List<OrderDetail> details = orderDetailService.getOrderDetail(queryParam);

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderMaster.getOrderId());
        result.put("memberId", orderMaster.getMemberId());
        result.put("storeId", orderMaster.getStoreId());
        result.put("status", orderMaster.getStatus().name());
        result.put("totalPrice", orderMaster.getTotalPrice());
        result.put("orderDetail", details);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Map<String, Object>>> getOrderList(@RequestParam(required = false) @Min(1) Integer memberId, @RequestParam(required = false) @Min(1) Integer storeId, @RequestParam(defaultValue = "CREATED") OrderStatus orderStatus, @RequestParam(defaultValue = "create_time") String orderBy, @RequestParam(defaultValue = "desc") String sort, @RequestParam(defaultValue = "1") Integer page) {
        OrderQueryParam queryParam = OrderQueryParam.builder().memberId(memberId).storeId(storeId).status(orderStatus).orderBy(orderBy).sort(sort).page(page).build();

        List<OrderMaster> list = orderService.getOrderList(queryParam);

        List<Map<String, Object>> maps = new ArrayList<>();

        for (OrderMaster om : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", om.getOrderId());
            map.put("memberId", om.getMemberId());
            map.put("storeId", om.getStoreId());
            map.put("status", om.getStatus().name());
            map.put("totalPrice", om.getTotalPrice());

            OrderDetailQueryParam param = OrderDetailQueryParam.builder().orderId(om.getOrderId()).orderBy("create_time").sort("desc").build();
            List<OrderDetail> details = orderDetailService.getOrderDetail(param);
            map.put("orderDetail", details);
            map.put("createTime", om.getCreateTime());
            map.put("modifyTime", om.getModifyTime());
            maps.add(map);
        }
        return ResponseEntity.status(HttpStatus.OK).body(maps);
    }
}
