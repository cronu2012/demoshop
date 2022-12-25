package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.OrderDetailDao;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public List<Map<String, Integer>> addOrderDetail(List<OrderDetail> orderDetails) {
        List<Map<String, Integer>> id = orderDetailDao.insert(orderDetails);
        return id;
    }

    @Override
    public Map<String, Integer> modifyOrderDetail(OrderDetail orderDetail) {
        Map<String, Integer> id = orderDetailDao.update(orderDetail);
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return id;
    }

    @Override
    public List<OrderDetail> getOrderDetail(OrderDetailQueryParam queryParam) {
        return orderDetailDao.selectOrder(queryParam);
    }
}
