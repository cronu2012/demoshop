package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.MemberDao;
import com.steven.demoshop.model.Member;
import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Integer register(Member member) {
        Member test = memberDao.selectOne(member.getMemberEmail());
        String phone = member.getPhone();
        if (test == null) {
            List<Member> members = memberDao.selectAll();
            for (Member m : members) {
                if (phone.equals(m.getPhone())) {
                    log.error("Phone: {} already used", phone);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
            //使用MD5生成密碼的雜湊值
            String hashedPassword = DigestUtils.md5DigestAsHex(member.getPassword().getBytes());
            member.setPassword(hashedPassword);
            return memberDao.insertOrUpdate(member);
        } else {
            log.error("Email: {} already used", member.getMemberEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Integer modifyMember(Member member) {
        Integer id = member.getMemberId();
        String email = member.getMemberEmail();
        String phone = member.getPhone();
        List<Member> members = memberDao.selectAll();
        for (Member m : members) {
            if (m.getMemberId() == id) continue;
            if (m.getMemberEmail().equals(email)) {
                log.warn("Email: {} already used", email);
                log.warn("Member: {}", m);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if (phone.equals(m.getPhone())) {
                log.warn("Phone: {} already used", phone);
                log.warn("Member: {}", m);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        //使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(member.getPassword().getBytes());
        member.setPassword(hashedPassword);
        return memberDao.insertOrUpdate(member);
    }

    @Override
    public Member login(String email, String password) {
        Member member = memberDao.selectOne(email);
        if (member == null) {
            log.error("Can not find email {} ", email);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            //使用MD5生成密碼的雜湊值
            String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            log.info(hashedPassword);
            log.info(member.getPassword());
            if (member.getPassword().equals(hashedPassword)) {
                log.info("Member {} login successful", email);
                return member;
            } else {
                log.error("Password is wrong", password);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
    }


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
    public void delete(Integer id) {
        memberDao.delete(id);
    }


}
