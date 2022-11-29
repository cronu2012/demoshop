package com.steven.demoshop.dao;

import com.steven.demoshop.model.Member;


import java.util.List;

public interface MemberDao {
    List<Member> selectAll();

    Member selectOne(Integer id);

    Member selectOne(String email);

    Integer insertOrUpdate(Member member);

    void delete(Integer id);
}
