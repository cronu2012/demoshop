package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.StoreDao;
import com.steven.demoshop.model.Store;
import com.steven.demoshop.rowmapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class StoreDapImpl implements StoreDao {

    private static final String INSERT =
            "insert into store (store_name,store_phone,intro) value (:name,:phone,:intro)";

    private static final String UPDATE =
            "update store set store_name = :name, store_phone = :phone, intro =:intro" +
                    " where store_id = :id";

    private static final String SELECT_ALL = "select store_id, store_name, store_phone, intro, create_time, modify_time" +
            " from store";

    private static final String SELECT_ID = "select store_id, store_name, store_phone, intro, create_time, modify_time" +
            " from store where store_id = :id";

    private static final String SELECT_NAME = "select store_id, store_name, store_phone, intro, create_time, modify_time" +
            " from store where store_name = :name";

    private static final String DELETE = "delete from store where store_id = :id";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Integer insertOrUpdate(Store store) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", store.getStoreName());
        map.put("phone", store.getStorePhone());
        map.put("intro", store.getIntro());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (store.getStoreId() == null) {
            jdbcTemplate.update(INSERT, new MapSqlParameterSource(map), keyHolder);
            Integer id = keyHolder.getKey().intValue();
            return id;
        } else {
            map.put("id", store.getStoreId());
            jdbcTemplate.update(UPDATE, map);
            return store.getStoreId();
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        jdbcTemplate.update(DELETE, new MapSqlParameterSource(map));
    }

    @Override
    public List<Store> selectAll() {
        List<Store> stores = jdbcTemplate.query(SELECT_ALL, new StoreMapper());
        if (stores.size() == 0) {
            return null;
        } else {
            log.info(stores.get(0).toString());
            return stores;
        }
    }

    @Override
    public Store selectOne(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Store> stores = jdbcTemplate.query(SELECT_ID, map, new StoreMapper());
        if (stores.size() == 0) {
            return null;
        } else {
            log.info(stores.get(0).toString());
            return stores.get(0);
        }
    }

    @Override
    public Store selectOne(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Store> stores = jdbcTemplate.query(SELECT_NAME, map, new StoreMapper());
        if (stores.size() == 0) {
            return null;
        } else {
            log.info(stores.get(0).toString());
            return stores.get(0);
        }
    }
}
