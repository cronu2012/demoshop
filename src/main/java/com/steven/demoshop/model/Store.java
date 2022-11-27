package com.steven.demoshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private Integer storeId;
    private String storeName;
    private String storePhone;
    private String intro;
    private Date createTime;
    private Date modifyTime;
}
