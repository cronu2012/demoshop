package com.steven.demoshop.model;

import com.steven.demoshop.constant.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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
    private Date createTime;
    private Date modifyTime;
}
