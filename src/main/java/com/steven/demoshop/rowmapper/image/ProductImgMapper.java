package com.steven.demoshop.rowmapper.image;

import com.steven.demoshop.constant.ImageStatus;
import com.steven.demoshop.model.image.MemberImage;
import com.steven.demoshop.model.image.ProductImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImgMapper implements RowMapper<ProductImage> {
    @Override
    public ProductImage mapRow(ResultSet rs, int row) throws SQLException {
        ProductImage images = ProductImage.builder()
                .imageId(rs.getInt("image_id"))
                .productId(rs.getInt("product_id"))
                .imgurUrl(rs.getString("imgur_url"))
                .status(ImageStatus.valueOf(rs.getString("status")))
                .createTime(rs.getTime("create_time").toLocalTime())
                .modifyTime(rs.getTime("modify_time").toLocalTime())
                .build();
        return images;
    }
}
