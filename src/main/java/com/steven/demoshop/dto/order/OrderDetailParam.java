package com.steven.demoshop.dto.order;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderDetailParam {
    @Min(1)
    @NotNull
    private Integer productId;
    @Range(min = 1, max = 10)
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer odPrice;
}
