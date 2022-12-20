package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.ProductDao;
import com.steven.demoshop.model.Product;
import com.steven.demoshop.rowmapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class ProductDaoImpl implements ProductDao {
    private static final String INSERT =
            "insert into product (store_id,category,product_name,product_price,info,stock,status) value" +
                    "(:storeId,:category,:productName,:productPrice,:info,:stock,:status)";

    private static final String UPDATE =
            "update product set category = :category,product_name = :productName, product_price=:price, info=:info,stock=:stock, status=:status " +
                    "where product_id = :productId";

    private static final String UPDATE_STOCK =
            "update product set stock = :stock where product_id = :productId";

    private static final String SELECT_ALL =
            "select product_id,store_id,category,product_name,product_price,info,stock,status,create_time,modify_time " +
                    "from product";

    private static final String SELECT_ID =
            "select product_id,store_id,category,product_name,product_price,info,stock,status,create_time,modify_time " +
                    "from product where product_id=:productId";

    private static final String SELECT_STORE =
            "select product_id,store_id,category,product_name,product_price,info,stock,status,create_time,modify_time " +
                    "from product where store_id=:storeId";

    private static final String DELETE =
            "delete from product where product_id=:productId";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Integer inserts(List<Product> products) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[products.size()];
        int i = 0;
        for (Product p : products) {
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("storeId", p.getStoreId());
            parameterSources[i].addValue("category", p.getCategory());
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
        Map<String, Object> map = new HashMap<>();
        map.put("category", product.getCategory());
        map.put("productName", product.getProductName());
        map.put("price", product.getPrice());
        map.put("info", product.getInfo());
        map.put("stock", product.getStock());
        map.put("status", product.getStatus().name());
        map.put("productId", product.getProductId());
        Integer result = jdbcTemplate.update(UPDATE, map);
        return result == 1 ? product.getProductId() : null;
    }

    @Override
    public Integer updatesStock(List<Map<String, Integer>> stockChanges) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[stockChanges.size()];
        int i = 0;
        for (Map map : stockChanges) {
            Integer id = (Integer) map.get("productId");
            Integer stock = (Integer) map.get("stock");
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("productId", id);
            parameterSources[i].addValue("stock", stock);
            i += 1;
        }
        int[] result = jdbcTemplate.batchUpdate(UPDATE_STOCK, parameterSources);
        log.info("已更新{}筆資料", result.length);
        return result.length;
    }

    @Override
    public List<Product> selectAll() {
        List<Product> list = jdbcTemplate.query(SELECT_ALL, new HashMap<>(), new ProductMapper());
        log.info("所有商品: {}", list);
        return list;
    }

    @Override
    public Product selectByID(Integer productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> products = jdbcTemplate.query(SELECT_ID, new MapSqlParameterSource(map), new ProductMapper());
        log.info("商品: {}", products.size() == 0 ? "無此商品ID" : products.get(0));
        return products.size() == 0 ? null : products.get(0);
    }

    @Override
    public List<Product> selectByStore(Integer storeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        List<Product> products = jdbcTemplate.query(SELECT_STORE, new MapSqlParameterSource(map), new ProductMapper());
        log.info("商店ID{} 的商品: {}", storeId, products.size() == 0 ? "無此商店ID" : products.get(0));
        return products.size() == 0 ? null : products;
    }

    @Override
    public void delete(Integer productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        Integer result = jdbcTemplate.update(DELETE, map);
        log.info("已刪除{}筆資料", result);
    }

    @Override
    public void deleteByStore(Integer storeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", storeId);
        Integer result = jdbcTemplate.update(DELETE, map);
        log.info("已刪除{}筆資料", result);
    }
}
