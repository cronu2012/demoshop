package com.steven.demoshop.rowmapper;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.model.Order;

import com.steven.demoshop.model.OrderMaster;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = Order.builder()
                .orderId(rs.getInt("om.order_id"))
                .memberId(rs.getInt("om.member_id"))
                .storeId(rs.getInt("om.store_id"))
                .status(OrderStatus.valueOf(rs.getString("om.status")))

                .productId(rs.getInt("od.product_id"))
                .quantity(rs.getInt("od.quantity"))
                .odPrice(rs.getInt("od.od_price"))

                .totalPrice(rs.getInt("om.total_price"))
                .createTime(rs.getTimestamp("om.create_time"))
                .modifyTime(rs.getTimestamp("om.modify_time"))
                .build();
        return order;
    }
}
