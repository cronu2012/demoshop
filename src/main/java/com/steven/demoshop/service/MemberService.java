package com.steven.demoshop.service;

import com.steven.demoshop.model.Member;

import java.util.List;

public interface MemberService {
    List<Member> getAll();

    Member getMember(Integer id);

    Member getMember(String email);

    Member login(String email, String password);

    Integer register(Member member);

    Integer modifyMember(Member member);

    void delete(Integer id);
}
