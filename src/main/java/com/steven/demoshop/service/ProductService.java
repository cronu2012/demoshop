package com.steven.demoshop.service;

import com.steven.demoshop.model.Product;

import java.util.List;

public interface ProductService {
    Integer addProduct(List<Product> products);

    Integer modifyProduct(Product product);

    List<Product> getAll();

    Product getProduct(Integer productId);

    List<Product> getByStore(Integer storeId);

    void delete(Integer productId);

    void deleteByStore(Integer storeId);
}
