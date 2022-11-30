package com.steven.demoshop.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class StoreRequest {
    @NotNull
    private String storeName;
    @NotNull
    private String storePhone;
    private String intro;
}
