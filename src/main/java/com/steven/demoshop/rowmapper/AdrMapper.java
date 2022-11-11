package com.steven.demoshop.rowmapper;

import com.steven.demoshop.constant.Permission;
import com.steven.demoshop.model.Administrator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdrMapper implements RowMapper<Administrator> {
    @Override
    public Administrator mapRow(ResultSet rs, int row) throws SQLException {
        Administrator administrator = Administrator.builder()
                .memberId(rs.getInt("member_id"))
                .storeId(rs.getInt("store_id"))
                .permission(Permission.valueOf(rs.getString("permission")))
                .createTime(rs.getTime("create_time").toLocalTime())
                .modifyTime(rs.getTime("modify_time").toLocalTime())
                .build();
        return administrator;
    }
}
