package com.steven.demoshop.dao;


import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.OrderMaster;

import java.util.List;

public interface OrderDao {
    Integer insert(OrderMaster orderMaster);

    Integer update(OrderMaster orderMaster);

    OrderMaster selectById(Integer orderId);

    List<OrderMaster> selectOrders(OrderQueryParam queryParam);

    void delete(Integer orderId);
}
