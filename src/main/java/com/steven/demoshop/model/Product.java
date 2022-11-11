package com.steven.demoshop.model;

import com.steven.demoshop.constant.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer productId;
    private Integer storeId;
    private String productName;
    private Integer price;
    private String info;
    private Integer stock;
    private ProductStatus status;
    private LocalTime createTime;
    private LocalTime modifyTime;
}
