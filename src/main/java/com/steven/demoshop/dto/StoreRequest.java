package com.steven.demoshop.dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class StoreRequest {
    @NotBlank
    @Pattern(regexp = "[\\u4e00-\\u9fa5_a-zA-Z\\d]{2,20}")
    private String storeName;
    @NotBlank
    @Pattern(regexp = "09\\d{2}-\\d{3}-\\d{3}")
    private String storePhone;
    @NotNull
    private String intro;
}
