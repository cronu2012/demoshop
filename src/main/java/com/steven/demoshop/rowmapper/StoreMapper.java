package com.steven.demoshop.rowmapper;

import com.steven.demoshop.model.Store;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreMapper implements RowMapper<Store> {
    @Override
    public Store mapRow(ResultSet rs, int row) throws SQLException {
        Store store = Store.builder()
                .storeId(rs.getInt("store_id"))
                .storeName(rs.getString("store_name"))
                .storePhone(rs.getString("store_phone"))
                .intro(rs.getString("intro"))
                .createTime(rs.getTimestamp("create_time"))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build();
        return store;
    }
}
