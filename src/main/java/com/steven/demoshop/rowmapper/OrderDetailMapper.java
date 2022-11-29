package com.steven.demoshop.rowmapper;

import com.steven.demoshop.model.OrderDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailMapper implements RowMapper<OrderDetail> {
    @Override
    public OrderDetail mapRow(ResultSet rs, int row) throws SQLException {
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(rs.getInt("order_id"))
                .productId(rs.getInt("product_id"))
                .quantity(rs.getInt("quantity"))
                .odPrice(rs.getInt("od_price"))
                .orderTime(rs.getTimestamp("order_time"))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build();
        return orderDetail;
    }
}
