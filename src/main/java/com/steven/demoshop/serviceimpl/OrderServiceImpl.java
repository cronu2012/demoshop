package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.dao.OrderDao;
import com.steven.demoshop.dto.order.OrderAdd;
import com.steven.demoshop.dto.order.OrderQueryParam;
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
    public Integer createOrder(OrderAdd orderAdd) {
        List<Map<String, Integer>> mapList = orderAdd.getDetail();
        int totalPrice = 0;
        for (Map<String, Integer> m : mapList) {
            totalPrice += m.get("price");
        }

        OrderMaster orderMaster = OrderMaster.builder()
                .memberId(orderAdd.getMemberId())
                .storeId(orderAdd.getStoreId())
                .status(OrderStatus.CREATED)
                .totalPrice(totalPrice)
                .build();

        Integer id = orderDao.insert(orderMaster);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return id;
    }

    @Override
    public Integer modifyOrder(OrderMaster orderMaster) {
        Integer id = orderDao.update(orderMaster);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return id;
    }

    @Override
    public List<OrderMaster> getOrderList(OrderQueryParam queryParam) {
        return orderDao.selectOrders(queryParam);
    }

    @Override
    public OrderMaster getOrder(Integer orderId) {
        OrderMaster orderMaster = orderDao.selectById(orderId);
        if (orderMaster == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return orderMaster;
    }

    @Override
    public void delete(Integer orderId) {
        orderDao.delete(orderId);
    }
}
