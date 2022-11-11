package com.steven.demoshop.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
