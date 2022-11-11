package com.steven.demoshop.Model;

import com.steven.demoshop.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;

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
