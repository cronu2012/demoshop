package com.steven.demoshop.service;

import com.steven.demoshop.dto.order.CreateOrderRequest;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.model.OrderMaster;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Integer createOrder(OrderMaster orderMaster) ;

    Integer modifyOrder(OrderMaster orderMaster);

    List<OrderMaster> getOrderList(OrderQueryParam queryParam);

    OrderMaster getOrder(Integer orderId);

    void delete(Integer orderId);

}
