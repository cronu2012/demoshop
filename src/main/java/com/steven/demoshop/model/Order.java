package com.steven.demoshop.model;

import com.steven.demoshop.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer orderId;
    private Integer memberId;
    private Integer storeId;

    private Integer productId;
    private Integer quantity;
    private Integer odPrice;

    private OrderStatus status;
    private Integer totalPrice;
    private Date createTime;
    private Date modifyTime;
}
