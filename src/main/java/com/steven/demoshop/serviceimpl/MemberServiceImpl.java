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
    public Member getOneById(Integer id) {
        return memberDao.selectOne(id);
    }

    @Override
    public Member getOneByEmail(String email) {
        return memberDao.selectOne(email);
    }

    //if no one match,return null
    @Override
    public Member isMember(String email, String password) {
        List<Member> members = memberDao.selectAll();

        Member result = null;
        for (Member m : members) {
            if (email.equals(m.getMemberEmail()) && password.equals(m.getPassword())) {
                result = m;
                break;
            }
        }
        return result;
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
