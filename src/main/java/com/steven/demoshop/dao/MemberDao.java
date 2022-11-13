package com.steven.demoshop.dao;

import com.steven.demoshop.model.Member;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface MemberDao {
    List<Member> selectAll();

    Member selectOneById(Integer id);

    Integer insertOrUpdate(Member member);


}
