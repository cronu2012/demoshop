package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.constant.Gender;
import com.steven.demoshop.dao.MemberDao;
import com.steven.demoshop.dto.MemberRequest;
import com.steven.demoshop.model.Member;
import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Integer register(MemberRequest memberRequest) {
        String email = memberRequest.getMemberEmail();
        String password = memberRequest.getPassword();
        String name = memberRequest.getMemberName();
        Gender gender = memberRequest.getGender();
        LocalDate birthday = memberRequest.getBirthday();
        String address = memberRequest.getAddress();
        String phone = memberRequest.getPhone();
        Member member = Member.builder()
                .memberEmail(email)
                .password(password)
                .memberName(name)
                .birthday(birthday)
                .gender(gender)
                .address(address)
                .phone(phone)
                .build();
        Member test = memberDao.selectOne(member.getMemberEmail());
        if (test == null) {
            return memberDao.insertOrUpdate(member);
        } else {
            log.error("Email: {} already used", member.getMemberEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Integer modifyData(MemberRequest memberRequest) {
        Integer id = memberRequest.getMemberId();
        String email = memberRequest.getMemberEmail();
        String password = memberRequest.getPassword();
        String name = memberRequest.getMemberName();
        Gender gender = memberRequest.getGender();
        LocalDate birthday = memberRequest.getBirthday();
        String address = memberRequest.getAddress();
        String phone = memberRequest.getPhone();

        List<Member> members = memberDao.selectAll();
        for (Member m : members) {
            if(m.getMemberId()==id) continue;
            if(m.getMemberEmail().equals(email)){
                log.warn("Email: {} already used",email);
                log.warn("Member: {}" ,m);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        Member member = Member.builder()
                .memberId(id)
                .memberEmail(email)
                .password(password)
                .memberName(name)
                .birthday(birthday)
                .gender(gender)
                .address(address)
                .phone(phone)
                .build();
        return memberDao.insertOrUpdate(member);
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
    public void delete(Integer id) {
        memberDao.delete(id);
    }


}
