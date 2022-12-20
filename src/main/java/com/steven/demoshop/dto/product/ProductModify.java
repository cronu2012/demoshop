package com.steven.demoshop.dto.product;

import com.steven.demoshop.constant.ProductCategory;
import com.steven.demoshop.constant.ProductStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ProductModify {
    private Integer storeId;
    private ProductCategory category;
    @Pattern(regexp = "[\\u4e00-\\u9fa5_a-zA-Z\\d]{3,20}")
    private String productName;
    private Integer price;
    private String info;
    private Integer stock;
    private ProductStatus status;
}
