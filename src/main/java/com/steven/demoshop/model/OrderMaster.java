package com.steven.demoshop.model;

import com.steven.demoshop.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMaster {
    private Integer orderId;
    private Integer memberId;
    private Integer storeId;
    private OrderStatus status;
    private Integer totalPrice;
    private Date orderTime;
    private Date modifyTime;
}
