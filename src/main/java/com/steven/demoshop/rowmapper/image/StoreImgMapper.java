package com.steven.demoshop.rowmapper.image;

import com.steven.demoshop.constant.ImageStatus;
import com.steven.demoshop.model.image.ProductImage;
import com.steven.demoshop.model.image.StoreImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreImgMapper implements RowMapper<StoreImage> {
    @Override
    public StoreImage mapRow(ResultSet rs, int row) throws SQLException {
        StoreImage images = StoreImage.builder()
                .imageId(rs.getInt("image_id"))
                .storeId(rs.getInt("store_id"))
                .imgurUrl(rs.getString("imgur_url"))
                .status(ImageStatus.valueOf(rs.getString("status")))
                .createTime(rs.getTimestamp("create_time"))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build();
        return images;
    }
}
