package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.AdminDao;
import com.steven.demoshop.model.Administrator;
import com.steven.demoshop.rowmapper.AdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class AdminDaoImpl implements AdminDao {

    private static final String SELECT_ALL = "select member_id, store_id,permission,create_time,modify_time" +
            " from administrator";

    private static final String SELECT_ONE = "select member_id, store_id,permission,create_time,modify_time" +
            " from administrator where member_id = :mid and store_id = :sid";

    private static final String SELECT_MEMBER = "select member_id, store_id,permission,create_time,modify_time" +
            " from administrator where member_id = :mid";

    private static final String SELECT_STORE = "select member_id, store_id,permission,create_time,modify_time" +
            " from administrator where store_id = :sid";

    private static final String UPDATE = "update administrator set permission = :per" +
            " where member_id = :mid and store_id = :sid";

    private static final String INSERT = "insert into administrator (member_id, store_id,permission) value" +
            " (:mid,:sin,:per)";

    private static final String DELETE = "delete from administrator where member_id = :mid and store_id = :sid";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Administrator> selectAll() {
        List<Administrator> admins = jdbcTemplate.query(SELECT_ALL, new AdminMapper());
        return admins;
    }

    @Override
    public Administrator selectOne(Integer memberId, Integer store_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", memberId);
        map.put("sid", store_id);
        List<Administrator> admins = jdbcTemplate.query(SELECT_ONE, map, new AdminMapper());
        if (admins.size() == 0) {
            return null;
        } else {
            return admins.get(0);
        }
    }

    @Override
    public List<Administrator> selectByMember(Integer memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", memberId);
        List<Administrator> admins = jdbcTemplate.query(SELECT_MEMBER, map, new AdminMapper());
        return admins;
    }

    @Override
    public List<Administrator> selectByStore(Integer storeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", storeId);
        List<Administrator> admins = jdbcTemplate.query(SELECT_STORE, map, new AdminMapper());
        return admins;
    }

    @Override
    public Integer insert(Administrator admin) {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", admin.getMemberId());
        map.put("sid", admin.getStoreId());
        map.put("per", admin.getPermission().name());
        return jdbcTemplate.update(INSERT, map);
    }

    @Override
    public Integer update(Administrator admin) {
        Map<String, Object> map = new HashMap<>();
        map.put("per", admin.getPermission().name());
        map.put("mid", admin.getMemberId());
        map.put("sid", admin.getStoreId());
        return jdbcTemplate.update(UPDATE, map);
    }

    @Override
    public void delete(Integer memberId, Integer store_id) {
        Map<String ,Object> map = new HashMap<>();
        map.put("mid", memberId);
        map.put("sid", store_id);
        jdbcTemplate.update(DELETE,map);
    }
}
