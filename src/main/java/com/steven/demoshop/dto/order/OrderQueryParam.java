package com.steven.demoshop.dto.order;

import com.steven.demoshop.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderQueryParam {
    Integer memberId;
    Integer storeId;
    OrderStatus status;
    String orderBy;
    String sort;
}
