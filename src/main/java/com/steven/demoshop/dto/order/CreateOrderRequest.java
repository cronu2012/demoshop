package com.steven.demoshop.dto.order;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class CreateOrderRequest {
    @Min(1)
    @NotNull
    private Integer storeId;

    private List<OrderDetailParam> details;
}
