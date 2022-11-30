package com.steven.demoshop.dao;

import com.steven.demoshop.model.Store;

import java.util.List;

public interface StoreDao {
    Integer insertOrUpdate(Store store);

    void delete(Integer id);

    List<Store> selectAll();

    Store selectOne(Integer id);

    Store selectOne(String name);
}
