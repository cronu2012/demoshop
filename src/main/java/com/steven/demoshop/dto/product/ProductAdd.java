package com.steven.demoshop.dto.product;

import com.steven.demoshop.constant.ProductStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ProductAdd {
    @NotNull
    private Integer storeId;
    @NotBlank
    @Pattern(regexp = "[\\u4e00-\\u9fa5_a-zA-Z\\d]{3,20}")
    private String productName;
    @NotNull
    private Integer price;
    @NotNull
    private String info;
    @NotNull
    private Integer stock;
    @NotNull
    private ProductStatus status;
}
