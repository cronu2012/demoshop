package com.steven.demoshop.dto.order;

import com.steven.demoshop.constant.OrderStatus;
import com.steven.demoshop.model.OrderDetail;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderAddVO {
    private Integer orderId;
    private Integer memberId;
    private Integer storeId;
    private OrderStatus status;
    private List<OrderDetail> details;
    private Integer totalPrice;
    private Date createTime;
    private Date modifyTime;
}
