package com.steven.demoshop.dao;

import com.steven.demoshop.model.Member;


import java.util.List;

public interface MemberDao {
    List<Member> selectAll();

    Member selectOneById(Integer id);

    Member selectOneByEmail(String email);

    Integer insertOrUpdate(Member member);

    void delete(Integer id);
}
