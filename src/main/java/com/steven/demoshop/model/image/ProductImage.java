package com.steven.demoshop.model.image;
import com.steven.demoshop.constant.ImageStatus;
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
public class ProductImage {
    private Integer imageId;
    private Integer productId;
    private String imgurUrl;
    private ImageStatus status;
    private Date createTime;
    private Date modifyTime;
}
