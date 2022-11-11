package com.steven.demoshop.model;

import com.steven.demoshop.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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
    private LocalTime orderTime;
    private LocalTime modifyTime;
}
