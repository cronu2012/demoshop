package com.steven.demoshop.Model;

import com.steven.demoshop.Enum.Permission;
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
public class Administrator {
    private Integer memberId;
    private Integer storeId;
    private Permission permission;
    private LocalTime createTime;
    private LocalTime modifyTime;
}
