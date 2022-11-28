package com.steven.demoshop.service;

import com.steven.demoshop.model.Member;

import java.util.List;

public interface MemberService {
    List<Member> getAll();

    Member getOneById(Integer id);

    Member getOneByEmail(String email);

    Member isMember(String email, String password);

    Integer insertOrUpdate(Member member);

    void delete(Integer id);
}
