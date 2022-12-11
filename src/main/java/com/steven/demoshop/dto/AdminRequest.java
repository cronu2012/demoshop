package com.steven.demoshop.dto;

import com.steven.demoshop.constant.Permission;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminRequest {
    @NotNull
    private Integer memberId;
    @NotNull
    private Integer storeId;
    @NotNull
    private Permission permission;
}
