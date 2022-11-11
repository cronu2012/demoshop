package com.steven.demoshop.Model;

import com.steven.demoshop.Enum.ProductStatus;
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
