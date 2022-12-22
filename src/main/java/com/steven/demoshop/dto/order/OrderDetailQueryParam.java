package com.steven.demoshop.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailQueryParam {
    Integer orderId;
    String orderBy;
    String sort;
}
