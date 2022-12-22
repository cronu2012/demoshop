package com.steven.demoshop.service;

import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.model.OrderDetail;

import java.util.List;
import java.util.Map;

public interface OrderDetailService {
    Map<String,Integer> addOrderDetail(OrderDetail orderDetail);

    Map<String,Integer> modifyOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> getOrderDetail(OrderDetailQueryParam queryParam);
}
