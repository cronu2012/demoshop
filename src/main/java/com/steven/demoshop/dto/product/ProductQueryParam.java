package com.steven.demoshop.dto.product;

import com.steven.demoshop.constant.ProductCategory;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;

@Data
@Builder
public class ProductQueryParam {
    Integer storeId;
    ProductCategory category;
    String productName;
    //ζεΊ
    String orderBy;
    String sort;
    //ει 
    Integer page;
}
