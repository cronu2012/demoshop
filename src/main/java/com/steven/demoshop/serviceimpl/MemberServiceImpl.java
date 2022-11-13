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
        return memberDao.selectOneById(id);
    }

    @Override
    public Member getOneByEmail(String email) {
        List<Member> members = memberDao.selectAll();

        Member result = null;
        for (Member m : members) {
            if (m.getMemberEmail().equals(email)) {
                result = m;
                break;
            }
        }
        return result;
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
    public Integer insert(Member member) {
        if (member.getMemberId() == null) {
            return memberDao.insertOrUpdate(member);
        }
        return 0;
    }

    @Override
    public Integer update(Member member) {
        if (member.getMemberId() != null) {
            return memberDao.insertOrUpdate(member);
        }
        return 0;
    }


}
