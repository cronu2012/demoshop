package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.OrderDao;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.OrderMaster;
import com.steven.demoshop.rowmapper.OrderMasterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderDaoImpl implements OrderDao {

    private static final String INSERT =
            "insert into order_master (member_id,store_id,status,total_price) value " +
                    "(:memberId,:storeId,:status,:totalPrice)";

    private static final String UPDATE =
            "update order_master set status = :status,total_price = :totalPrice " +
                    "where order_id = :orderId";

    private static final String SELECT =
            "select order_id,member_id,store_id,status,total_price,create_time,modify_time" +
                    " from order_master where 1=1";

    private static final String DELETE =
            "delete from order_master where order_id = :orderId";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Integer insert(OrderMaster orderMaster) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", orderMaster.getMemberId());
        map.put("storeId", orderMaster.getStoreId());
        map.put("status", orderMaster.getStatus().name());
        map.put("totalPrice", orderMaster.getTotalPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        Integer result = jdbcTemplate.update(INSERT, new MapSqlParameterSource(map), keyHolder);

        Integer orderId = keyHolder.getKey().intValue();

        return result == 1 ? orderId : null;
    }

    @Override
    public Integer update(OrderMaster orderMaster) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", orderMaster.getStatus().name());
        map.put("totalPrice", orderMaster.getTotalPrice());
        map.put("orderId", orderMaster.getOrderId());

        Integer result = jdbcTemplate.update(UPDATE, map);
        return result == 1 ? orderMaster.getOrderId() : null;
    }

    @Override
    public OrderMaster selectById(Integer orderId) {
        String SELECT_ID = SELECT;
        SELECT_ID += " and order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderMaster> orderMasters = jdbcTemplate.query(SELECT_ID, map, new OrderMasterMapper());
        return orderMasters.size() != 0 ? orderMasters.get(0) : null;
    }

    @Override
    public List<OrderMaster> selectOrders(OrderQueryParam queryParam) {
        StringBuilder sql = new StringBuilder(SELECT);
        Map<String, Object> map = new HashMap<>();

        if (queryParam.getMemberId() != null) {
            sql.append(" and member_id = :memberId");
            map.put("memberId", queryParam.getMemberId());
        }
        if (queryParam.getStoreId() != null) {
            sql.append(" and store_id = :storeId");
            map.put("storeId", queryParam.getStoreId());
        }
        if (queryParam.getStatus() != null) {
            sql.append(" and status = :status");
            map.put("status", queryParam.getStatus().name());
        }
        sql.append(" order by " + queryParam.getOrderBy() + " " + queryParam.getSort());
        sql.append(" limit :limit offset :offset");
        map.put("limit", 6);
        map.put("offset", (queryParam.getPage() - 1) * 6);

        String QUERY = sql.toString();

        return jdbcTemplate.query(QUERY, map, new OrderMasterMapper());
    }

    @Override
    public void delete(Integer orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        jdbcTemplate.update(DELETE, map);
    }
}
