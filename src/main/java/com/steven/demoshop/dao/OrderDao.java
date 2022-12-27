package com.steven.demoshop.dao;


import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.Order;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.model.OrderMaster;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    Integer insertOrder(OrderMaster orderMaster);

    OrderMaster selectOrderById(Integer orderId);

    Integer updateOrder(OrderMaster orderMaster);
    Map<String,Integer> updateDetails(OrderDetail orderDetail);

    List<OrderMaster> selectOrders(OrderQueryParam queryParam);

    void deleteOrder(Integer orderId);




}
