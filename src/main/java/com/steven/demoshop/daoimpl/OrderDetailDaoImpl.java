package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.OrderDetailDao;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.rowmapper.OrderDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderDetailDaoImpl implements OrderDetailDao {

    private static final String INSERT =
            "insert into order_detail (order_id,product_id,quantity,od_price) value" +
                    " (order_id = :orderId,product_id = :productId,quantity = :quantity,od_price = :odPrice)";

    private static final String UPDATE =
            "update order_detail set quantity = :quantity,od_price = :odPrice where order_id = :orderId and" +
                    " product_id = :productId";

    private static final String SELECT =
            "select order_id,product_id,quantity,od_price,create_time,modify_time" +
                    " from order_detail where 1=1 ";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Integer> insert(OrderDetail orderDetail) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderDetail.getOrderId());
        map.put("productId", orderDetail.getProductId());
        map.put("quantity", orderDetail.getQuantity());
        map.put("odPrice", orderDetail.getOdPrice());
        Integer result = jdbcTemplate.update(INSERT, map);

        Map<String, Integer> id = new HashMap<>();
        id.put("orderId", orderDetail.getOrderId());
        id.put("productId", orderDetail.getProductId());
        return result == 1 ? id : null;
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
