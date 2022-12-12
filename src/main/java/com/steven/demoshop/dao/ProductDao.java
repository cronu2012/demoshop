package com.steven.demoshop.dao;

import com.steven.demoshop.model.Product;

import java.util.List;

public interface ProductDao {
    Integer inserts(List<Product> products);

    Integer update(Product product);

    List<Product> selectAll();

    Product selectByID(Integer productId);

    List<Product> selectByStore(Integer storeId);

    void delete(Integer productId);
}
