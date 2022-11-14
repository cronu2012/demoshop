package com.steven.demoshop.daoimpl;

import com.steven.demoshop.dao.MemberDao;
import com.steven.demoshop.model.Member;
import com.steven.demoshop.rowmapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MemberDaoImpl implements MemberDao {

    private static final String INSERT =
            "insert into member (member_email,password,member_name,gender,birthday,address,phone) value" +
                    "(:email,:password,:name,:gender,:birthday,:address,:phone)";

    private static final String UPDATE =
            "update member" +
                    "set member_email =:email,password = :password,member_name=:name,gender=:gender," +
                    "birthday= :birthday,address=:address ,phone=:phone where member_id= :id; ";

    private static final String SELECT_ALL =
            "select member_id,member_email,password,member_name,gender,birthday,address,phone,create_time,modify_time" +
                    "from member";

    private static final String SELECT_ONE =
            "select member_id,member_email,password,member_name,gender,birthday,address,phone,create_time,modify_time" +
                    "from member where member_id = :id";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Member> selectAll() {
        List<Member> members = null;
        try {
            members = jdbcTemplate.query(SELECT_ALL, new HashMap<>(), new MemberMapper());
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
        }
        log.info(members.toString());
        return members;
    }

    @Override
    public Member selectOneById(Integer id) {
        List<Member> members = null;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        try {
            members = jdbcTemplate.query(SELECT_ONE, map, new MemberMapper());
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
        }
        log.info(members.toString());

        return members.get(0);
    }


    @Override
    public Integer insertOrUpdate(Member member) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", member.getMemberEmail());
        map.put("password", member.getPassword());
        map.put("name", member.getMemberName());
        map.put("gender", member.getGender().name());
        map.put("birthday", member.getBirthday());
        map.put("address", member.getAddress());
        map.put("phone", member.getPhone());
        if (member.getMemberId() != null) {
            map.put("id", member.getMemberId());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            if (member.getMemberId() == null) {
                jdbcTemplate.update(INSERT, new MapSqlParameterSource(map), keyHolder);
                Integer id = keyHolder.getKey().intValue();
                return id;
            } else {
                jdbcTemplate.update(UPDATE, map);
                return member.getMemberId();
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return null;
        }
    }


}
