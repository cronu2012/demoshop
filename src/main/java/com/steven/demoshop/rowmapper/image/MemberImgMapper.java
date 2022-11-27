package com.steven.demoshop.rowmapper.image;

import com.steven.demoshop.constant.ImageStatus;
import com.steven.demoshop.model.image.MemberImage;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class MemberImgMapper implements RowMapper<MemberImage> {
    @Override
    public MemberImage mapRow(ResultSet rs, int row) throws SQLException {

        MemberImage images = MemberImage.builder()
                .imageId(rs.getInt("image_id"))
                .memberId(rs.getInt("member_id"))
                .imgurUrl(rs.getString("imgur_url"))
                .status(ImageStatus.valueOf(rs.getString("status")))
                .createTime(rs.getTimestamp("create_time"))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build();
        return images;
    }
}
