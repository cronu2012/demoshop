package com.steven.demoshop.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private Integer storeId;
    private String storeName;
    private String storePhone;
    private String intro;
    private LocalTime createTime;
    private LocalTime modifyTime;
}
