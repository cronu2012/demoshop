package com.steven.demoshop.service;

import com.steven.demoshop.dto.order.OrderAdd;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.OrderMaster;

import java.util.List;

public interface OrderService {
    Integer createOrder(OrderAdd orderAdd) ;

    Integer modifyOrder(OrderMaster orderMaster);

    List<OrderMaster> getOrderList(OrderQueryParam queryParam);

    OrderMaster getOrder(Integer orderId);

    void delete(Integer orderId);
}
