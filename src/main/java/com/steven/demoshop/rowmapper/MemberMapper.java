package com.steven.demoshop.rowmapper;
import com.steven.demoshop.constant.Gender;
import com.steven.demoshop.model.Member;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                .createTime((rs.getTimestamp("create_time")))
                .modifyTime(rs.getTimestamp("modify_time"))
                .build();

        return member;
    }
}
