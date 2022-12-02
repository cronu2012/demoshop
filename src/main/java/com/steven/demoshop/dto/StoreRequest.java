package com.steven.demoshop.dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Data
public class StoreRequest {
    @NotNull
    private String storeName;
    @NotNull
    private String storePhone;
    private String intro;
}
