package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.MemberDao;
import com.steven.demoshop.model.Member;
import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Member> getAll() {
        return memberDao.selectAll();
    }

    @Override
    public Member getMember(Integer id) {
        return memberDao.selectOne(id);
    }

    @Override
    public Member getMember(String email) {
        return memberDao.selectOne(email);
    }

    //if no one match,return null
    @Override
    public Member isMember(String email, String password) {
        Member member = memberDao.selectOne(email);
        if (member == null) {
            return null;
        } else {
            if (member.getPassword().equals(password)) {
                member.setPassword("*****");
                return member;
            } else {
                return null;
            }
        }
    }

    @Override
    public Integer insertOrUpdate(Member member) {
        return memberDao.insertOrUpdate(member);
    }


    @Override
    public void delete(Integer id) {
        memberDao.delete(id);
    }


}
