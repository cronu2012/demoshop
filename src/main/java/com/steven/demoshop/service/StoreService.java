package com.steven.demoshop.service;

import com.steven.demoshop.model.Store;

import java.util.List;

public interface StoreService {
    Integer insertOrUpdate(Store store);

    void delete(Integer id);

    List<Store> getStores();

    Store getStore(Integer id);

    Store getStore(String name);

}
