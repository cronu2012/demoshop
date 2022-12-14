package com.steven.demoshop.service;

import com.steven.demoshop.dto.product.ProductQueryParam;
import com.steven.demoshop.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Integer addProduct(List<Product> products);

    Integer modifyProduct(Product product);

    Integer modifyProduct(List<Map<String,Integer>> orderList);

    List<Product> getAll();

    Product getProduct(Integer productId);

    List<Product> getProducts(ProductQueryParam queryParam);

    void delete(Integer productId);

    void deleteByStore(Integer storeId);
}
