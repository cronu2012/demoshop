package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.OrderDetailDao;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.rowmapper.OrderDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderDetailDaoImpl implements OrderDetailDao {

    private static final String INSERT =
            "insert into order_detail (order_id,product_id,quantity,od_price) value" +
                    " (:orderId,:productId,:quantity,:odPrice)";

    private static final String UPDATE =
            "update order_detail set quantity = :quantity,od_price = :odPrice where order_id = :orderId and" +
                    " product_id = :productId";

    private static final String SELECT =
            "select order_id,product_id,quantity,od_price,create_time,modify_time" +
                    " from order_detail where 1=1 ";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Integer>> insert(List<OrderDetail> details) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[details.size()];

        int index = 0;
        for (OrderDetail orderDetail : details) {
            parameterSources[index] = new MapSqlParameterSource();
            parameterSources[index].addValue("orderId", orderDetail.getOrderId());
            parameterSources[index].addValue("productId", orderDetail.getProductId());
            parameterSources[index].addValue("quantity", orderDetail.getQuantity());
            parameterSources[index].addValue("odPrice", orderDetail.getOdPrice());
            index += 1;
        }

        int[] result = jdbcTemplate.batchUpdate(INSERT, parameterSources);

        List<Map<String, Integer>> mapList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 1) {
                Map<String, Integer> id = new HashMap<>();
                id.put("orderId", details.get(i).getOrderId());
                id.put("productId",  details.get(i).getProductId());
                mapList.add(id);
            }
        }
        return mapList;
    }

    @Override
    public Map<String, Integer> update(OrderDetail orderDetail) {
        Map<String, Object> map = new HashMap<>();
        map.put("quantity", orderDetail.getQuantity());
        map.put("odPrice", orderDetail.getOdPrice());
        map.put("orderId", orderDetail.getOrderId());
        map.put("productId", orderDetail.getProductId());
        Integer result = jdbcTemplate.update(UPDATE, map);

        Map<String, Integer> id = new HashMap<>();
        id.put("orderId", orderDetail.getOrderId());
        id.put("productId", orderDetail.getProductId());
        return result == 1 ? id : null;
    }

    @Override
    public List<OrderDetail> selectOrder(OrderDetailQueryParam queryParam) {
        StringBuilder sql = new StringBuilder(SELECT);
        Map<String, Object> map = new HashMap<>();
        sql.append(" and order_id = :orderId");
        map.put("orderId", queryParam.getOrderId());

        sql.append(" order by " + queryParam.getOrderBy() + " " + queryParam.getSort());


        String QUERY = sql.toString();
        List<OrderDetail> list = jdbcTemplate.query(QUERY, map, new OrderDetailMapper());
        return list;
    }
}
