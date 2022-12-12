package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.ProductDao;
import com.steven.demoshop.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductDaoImpl implements ProductDao {
    private static final String INSERT =
            "insert into product (store_id,product_name,product_price,info,stock,status) value" +
                    "(:storeId,:productName,:productPrice,:info,:stock,:status)";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Integer inserts(List<Product> products) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[products.size()];
        int i = 0;
        for (Product p : products) {
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("storeId", p.getStoreId());
            parameterSources[i].addValue("productName", p.getProductName());
            parameterSources[i].addValue("productPrice", p.getPrice());
            parameterSources[i].addValue("info", p.getInfo());
            parameterSources[i].addValue("stock", p.getStock());
            parameterSources[i].addValue("status", p.getStatus().name());
            i += 1;
        }
        int[] array = jdbcTemplate.batchUpdate(INSERT, parameterSources);
        return array.length;
    }

    @Override
    public Integer update(Product product) {
        return null;
    }

    @Override
    public List<Product> selectAll() {
        return null;
    }

    @Override
    public Product selectByID(Integer productId) {
        return null;
    }

    @Override
    public List<Product> selectByStore(Integer storeId) {
        return null;
    }

    @Override
    public void delete(Integer productId) {

    }
}
