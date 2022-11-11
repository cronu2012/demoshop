package com.steven.demoshop.model.image;


import com.steven.demoshop.constant.ImageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberImage {
    private Integer imageId;
    private Integer memberId;
    private String imgurUrl;
    private ImageStatus status;
    private LocalTime createTime;
    private LocalTime modifyTime;
}
