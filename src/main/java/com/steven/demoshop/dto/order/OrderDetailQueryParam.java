package com.steven.demoshop.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class OrderDetailQueryParam {
    Integer orderId ;
    String orderBy;
    String sort;
}
