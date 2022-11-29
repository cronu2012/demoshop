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
                    " set member_email =:email,password = :password,member_name=:name,gender=:gender," +
                    "birthday= :birthday,address=:address ,phone=:phone where member_id= :id; ";

    private static final String SELECT_ALL =
            "select member_id,member_email,password,member_name,gender,birthday,address,phone,create_time,modify_time" +
                    " from member";

    private static final String SELECT_ONE =
            "select member_id,member_email,password,member_name,gender,birthday,address,phone,create_time,modify_time" +
                    " from member where member_id = :id";

    private static final String SELECT_ONE_EMAIL =
            "select member_id,member_email,password,member_name,gender,birthday,address,phone,create_time,modify_time" +
                    " from member where member_email = :email";

    private static final String DELETE_ONE =
            "delete from member where member_id = :id";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Member> selectAll() {
        List<Member> members = jdbcTemplate.query(SELECT_ALL, new HashMap<>(), new MemberMapper());
        log.info(members.toString());
        return members;
    }

    @Override
    public Member selectOneById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Member> members = jdbcTemplate.query(SELECT_ONE, map, new MemberMapper());
        if (members.size() != 0) {
            log.info(members.get(0).toString());
            return members.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Member selectOneByEmail(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        List<Member> members = jdbcTemplate.query(SELECT_ONE_EMAIL, map, new MemberMapper());
        if (members.size() != 0) {
            log.info(members.get(0).toString());
            return members.get(0);
        } else {
            return null;
        }
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
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if (member.getMemberId() == null) {
            jdbcTemplate.update(INSERT, new MapSqlParameterSource(map), keyHolder);
            Integer id = keyHolder.getKey().intValue();
            return id;
        } else {
            map.put("id", member.getMemberId());
            jdbcTemplate.update(UPDATE, map);
            return member.getMemberId();
        }

    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        jdbcTemplate.update(DELETE_ONE, map);

    }


}
