package com.steven.demoshop.dao;

import com.steven.demoshop.model.Administrator;

import java.util.List;

public interface AdminDao {
    List<Administrator> selectAll();

    Administrator selectOne(Integer memberId, Integer store_id);

    List<Administrator> selectByMember(Integer memberId);

    List<Administrator> selectByStore(Integer storeId);

    Integer insert(Administrator admin);

    Integer update(Administrator admin);

    void delete(Integer memberId, Integer store_id);
}
