package com.steven.demoshop.dao;

import com.steven.demoshop.dto.product.ProductQueryParam;
import com.steven.demoshop.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    Integer inserts(List<Product> products);//回傳執行幾筆資料

    Integer update(Product product);//回傳ID

    Integer updatesStock(List<Map<String, Integer>> stockChanges);//回傳執行幾筆資料

    List<Product> selectAll();//回傳所有商品List

    Product selectByID(Integer productId);//回傳一件商品物件

    List<Product> selectQuery(ProductQueryParam queryParam);//回傳一家商店若干商品物件

    void delete(Integer productId);//回傳執行幾筆資料

    void deleteByStore(Integer storeId);
}
