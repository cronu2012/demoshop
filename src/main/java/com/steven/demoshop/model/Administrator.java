package com.steven.demoshop.model;

import com.steven.demoshop.constant.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Administrator {
    private Integer memberId;
    private Integer storeId;
    private Permission permission;
    private Date createTime;
    private Date modifyTime;
}
