package com.steven.demoshop.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer odPrice;
    private LocalTime orderTime;
    private LocalTime modifyTime;
}
