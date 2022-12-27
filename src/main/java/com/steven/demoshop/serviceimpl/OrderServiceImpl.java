package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.dao.OrderDao;
import com.steven.demoshop.dto.order.CreateOrderRequest;
import com.steven.demoshop.dto.order.OrderDetailParam;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.model.OrderMaster;
import com.steven.demoshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Integer createOrder(OrderMaster orderMaster) {
        Integer id = orderDao.insertOrder(orderMaster);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return id;
    }

    @Override
    public OrderMaster getOrder(Integer orderId) {
        OrderMaster orderMaster = orderDao.selectOrderById(orderId);
        if (orderMaster == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return orderMaster;
    }
    @Override
    public List<OrderMaster> getOrderList(OrderQueryParam queryParam) {
        return orderDao.selectOrders(queryParam);
    }

    @Override
    public Integer modifyOrder(OrderMaster orderMaster) {
        Integer id = orderDao.updateOrder(orderMaster);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return id;
    }



    @Override
    public void delete(Integer orderId) {
        orderDao.deleteOrder(orderId);
    }


}
