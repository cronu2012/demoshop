package com.steven.demoshop.model;


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
public class OrderDetail {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer odPrice;
    private Date createTime;
    private Date modifyTime;
}
