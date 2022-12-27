package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.OrderDao;
import com.steven.demoshop.dto.order.OrderDetailQueryParam;
import com.steven.demoshop.dto.order.OrderQueryParam;
import com.steven.demoshop.model.Order;
import com.steven.demoshop.model.OrderDetail;
import com.steven.demoshop.model.OrderMaster;
import com.steven.demoshop.rowmapper.OrderDetailMapper;
import com.steven.demoshop.rowmapper.OrderMapper;
import com.steven.demoshop.rowmapper.OrderMasterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderDaoImpl implements OrderDao {
    private static final String UPDATE =
            "update order_master set status = :status,total_price = :totalPrice " +
                    "where order_id = :orderId";

    private static final String DELETE =
            "delete from order_master where order_id = :orderId";

    private static final String UPDATE_OD =
            "update order_detail set quantity = :quantity,od_price = :odPrice where order_id = :orderId and" +
                    " product_id = :productId";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Integer insertOrder(OrderMaster orderMaster) {
        String INSERT_OM =
                "insert into order_master (member_id,store_id,status,total_price) value " +
                        "(:memberId,:storeId,:status,:totalPrice)";
        String INSERT_OD =
                "insert into order_detail (order_id,product_id,quantity,od_price) value" +
                        " (:orderId,:productId,:quantity,:odPrice)";
        //新增訂單
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", orderMaster.getMemberId());
        map.put("storeId", orderMaster.getStoreId());
        map.put("status", orderMaster.getStatus().name());
        map.put("totalPrice", orderMaster.getTotalPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Integer result = jdbcTemplate.update(INSERT_OM, new MapSqlParameterSource(map), keyHolder);
        Integer orderId = keyHolder.getKey().intValue();

        //新增訂單明細商品
        List<OrderDetail> details = orderMaster.getOrderDetails();
        for (OrderDetail od : details) {
            od.setOrderId(orderId);
        }
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
        int[] batchUpdate = jdbcTemplate.batchUpdate(INSERT_OD, parameterSources);
        for (int i = 0; i < batchUpdate.length; i++) {
            if (batchUpdate[i] != 1) {
                log.warn("第 {} 筆資料新增失敗", i + 1);
            }
        }
        //返回新訂單ID
        return result == 1 ? orderId : null;
    }

    @Override
    public OrderMaster selectOrderById(Integer orderId) {
        String sql = "select om.order_id, om.member_id,om.store_id,om.status,om.total_price,od.product_id,od.quantity,od.od_price," +
                " om.create_time,om.modify_time" +
                " from order_master om  left join order_detail od on om.order_id = od.order_id" +
                " where om.order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orders = jdbcTemplate.query(sql, map, new OrderMapper());

        if (orders.size() == 0) {
            return null;
        }
        Order example = orders.get(0);
        List<OrderDetail> details = new ArrayList<>();
        for (Order od : orders) {
            OrderDetail detail = new OrderDetail();
            detail.setProductId(od.getProductId());
            detail.setQuantity(od.getQuantity());
            detail.setOdPrice(od.getOdPrice());
            detail.setCreateTime(od.getCreateTime());
            detail.setModifyTime(od.getModifyTime());

            details.add(detail);
        }
        OrderMaster orderMaster = OrderMaster.builder()
                .orderId(example.getOrderId())
                .memberId(example.getMemberId())
                .storeId(example.getStoreId())
                .status(example.getStatus())
                .totalPrice(example.getTotalPrice())
                .orderDetails(details)
                .createTime(example.getCreateTime())
                .modifyTime(example.getModifyTime())
                .build();

        return orderMaster;
    }

    @Override
    public List<OrderMaster> selectOrders(OrderQueryParam queryParam) {
        String sql = "select om.order_id, om.member_id,om.store_id,om.status,om.total_price,od.product_id,od.quantity,od.od_price," +
                " om.create_time,om.modify_time" +
                " from order_master om  left join order_detail od on om.order_id = od.order_id" +
                " where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        Map<String, Object> map = new HashMap<>();
        if (queryParam.getMemberId() != null) {
            sb.append(" and member_id = :memberId");
            map.put("memberId", queryParam.getMemberId());
        }
        if (queryParam.getStoreId() != null) {
            sb.append(" and store_id = :storeId");
            map.put("storeId", queryParam.getStoreId());
        }
        if (queryParam.getStatus() != null) {
            sb.append(" and status = :status");
            map.put("status", queryParam.getStatus().name());
        }
        sb.append(" order by " + queryParam.getOrderBy() + " " + queryParam.getSort());
//        sb.append(" limit :limit offset :offset");
//        map.put("limit", 6);
//        map.put("offset", (queryParam.getPage() - 1) * 6);
        String QUERY = sb.toString();

        List<Order> orders = jdbcTemplate.query(QUERY, map, new OrderMapper());

        if (orders.size() == 0) {
            return null;
        }

        List<OrderMaster> masters = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            Order orderA = orders.get(i);

            List<OrderDetail> details = new ArrayList<>();

            int nextIndex = 0; //下一個外迴圈的序列值
            for (int j = i; j < orders.size(); j++) {
                Order orderB = orders.get(j);
                if (orderA.getOrderId() == orderB.getOrderId()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setProductId(orderB.getProductId());
                    detail.setQuantity(orderB.getQuantity());
                    detail.setOdPrice(orderB.getOdPrice());
                    detail.setCreateTime(orderB.getCreateTime());
                    detail.setModifyTime(orderB.getModifyTime());
                    details.add(detail);
                    nextIndex = j ;
                }

            }
            OrderMaster master = OrderMaster.builder()
                    .orderId(orderA.getOrderId())
                    .memberId(orderA.getMemberId())
                    .storeId(orderA.getStoreId())
                    .status(orderA.getStatus())
                    .orderDetails(details)
                    .totalPrice(orderA.getTotalPrice())
                    .createTime(orderA.getCreateTime())
                    .modifyTime(orderA.getModifyTime())
                    .build();
            masters.add(master);
            
            i = nextIndex;
        }


        return masters;
    }

    @Override
    public Integer updateOrder(OrderMaster orderMaster) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", orderMaster.getStatus().name());
        map.put("totalPrice", orderMaster.getTotalPrice());
        map.put("orderId", orderMaster.getOrderId());

        Integer result = jdbcTemplate.update(UPDATE, map);
        return result == 1 ? orderMaster.getOrderId() : null;
    }


    @Override
    public void deleteOrder(Integer orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        jdbcTemplate.update(DELETE, map);
    }


    @Override
    public Map<String, Integer> updateDetails(OrderDetail orderDetail) {

        return null;
    }


}
