package com.steven.demoshop.dao;

import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.model.OrderDetail;

import java.util.List;
import java.util.Map;

public interface OrderDetailDao {
    List<Map<String, Integer>> insert(List<OrderDetail> orderDetails);

    Map<String,Integer> update(OrderDetail orderDetail);

    List<OrderDetail> selectOrder(OrderDetailQueryParam queryParam);

}
