package com.steven.demoshop.rowmapper;

import com.steven.demoshop.constant.ProductStatus;
import com.steven.demoshop.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int row) throws SQLException {
        Product product = Product.builder()
                .productId(rs.getInt("product_id"))
                .storeId(rs.getInt("store_id"))
                .productName(rs.getString("product_name"))
                .price(rs.getInt("product_price"))
                .info(rs.getString("info"))
                .stock(rs.getInt("stock"))
                .status(ProductStatus.valueOf(rs.getString("status")))
                .createTime(rs.getTime("create_time").toLocalTime())
                .modifyTime(rs.getTime("modify_time").toLocalTime())
                .build();
        return product;
    }
}
