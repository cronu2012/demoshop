package com.steven.demoshop.rowmapper;

import com.steven.demoshop.constant.Gender;
import com.steven.demoshop.model.Member;
import lombok.Builder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class MemberMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int row) throws SQLException {
        Member member = Member.builder()
                .memberId(rs.getInt("member_id"))
                .memberEmail(rs.getString("member_email"))
                .password(rs.getString("password"))
                .memberName(rs.getString("member_name"))
                .gender(Gender.valueOf(rs.getString("gender")))
                .birthday(rs.getDate("birthday").toLocalDate())
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .createTime(rs.getTime("create_time").toLocalTime())
                .modifyTime(rs.getTime("modify_time").toLocalTime())
                .build();

        return member;
    }
}
