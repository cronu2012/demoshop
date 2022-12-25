package com.steven.demoshop.dto.order;

import com.steven.demoshop.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class OrderAdd {
    @Min(1)
    @NotNull
    private Integer memberId;
    @Min(1)
    @NotNull
    private Integer storeId;

    private List<Map<String,Integer>> detail;
}
