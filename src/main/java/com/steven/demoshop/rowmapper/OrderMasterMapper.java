package com.steven.demoshop.rowmapper;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.model.OrderMaster;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMasterMapper implements RowMapper<OrderMaster> {
    @Override
    public OrderMaster mapRow(ResultSet rs, int row) throws SQLException {
        OrderMaster orderMaster = OrderMaster.builder()
                .orderId(rs.getInt("order_id"))
                .memberId(rs.getInt("member_id"))
                .storeId(rs.getInt("store_id"))
                .status(OrderStatus.valueOf(rs.getString("status")))
                .totalPrice(rs.getInt("total_price"))
                .orderTime(rs.getTime("order_time").toLocalTime())
                .modifyTime(rs.getTime("modify_time").toLocalTime())
                .build();
        return orderMaster;
    }
}
