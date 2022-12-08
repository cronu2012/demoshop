package com.steven.demoshop.controller;

import com.steven.demoshop.constant.Gender;
import com.steven.demoshop.constant.Regex;
import com.steven.demoshop.dto.MemberRequest;
import com.steven.demoshop.model.Member;
import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/demoshop")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Member> register(@RequestBody @Valid MemberRequest memberRequest) {
        Member member = Member.builder()
                .memberEmail(memberRequest.getMemberEmail())
                .password(memberRequest.getPassword())
                .memberName(memberRequest.getMemberName())
                .birthday(memberRequest.getBirthday())
                .gender(memberRequest.getGender())
                .address(memberRequest.getAddress())
                .phone(memberRequest.getPhone())
                .build();
        Integer id = memberService.register(member);
        if (id == null) {
            log.error("creation failed");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Member result = memberService.getMember(id);
        if (result == null) {
            log.error("creation failed");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            log.info("creation success");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> modifyMember(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid MemberRequest memberRequest
    ) {
        Member member = Member.builder()
                .memberEmail(memberRequest.getMemberEmail())
                .password(memberRequest.getPassword())
                .memberName(memberRequest.getMemberName())
                .birthday(memberRequest.getBirthday())
                .gender(memberRequest.getGender())
                .address(memberRequest.getAddress())
                .phone(memberRequest.getPhone())
                .build();

        Member oldMember = memberService.getMember(id);
        if (oldMember == null) {
            log.error("member not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            memberRequest.setMemberId(id);
            Integer memberId = memberService.modifyMember(member);
            Member result = memberService.getMember(memberId);
            if (result == null) {
                log.error("update failed");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else {
                log.info("update success");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable @Min(1) Integer id) {
        Member member = memberService.getMember(id);
        if (member != null) {
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            log.error("member null");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@RequestParam(required = false) String email) {
        if (email == null) {
            List<Member> members = memberService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(members);
        }
        if (!(email.matches(Regex.EMAIL.getRegexString()))) {
            log.error("email error");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Member member = memberService.getMember(email);

        if (member != null) {
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable @Min(1) Integer id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public boolean checkMemberParam(MemberRequest memberRequest) {
        boolean isCurrent = true;
        String email = memberRequest.getMemberEmail();
        String password = memberRequest.getPassword();
        String name = memberRequest.getMemberName();
        Gender gender = memberRequest.getGender();
        LocalDate birthday = memberRequest.getBirthday();
        String address = memberRequest.getAddress();
        String phone = memberRequest.getPhone();

        if (!email.matches(Regex.EMAIL.getRegexString())) {
            isCurrent = false;
            log.error("email error");
        }

        if (!password.matches(Regex.PASSWORD.getRegexString())) {
            isCurrent = false;
            log.error("password error");
        }

        if (!name.matches(Regex.NAME.getRegexString())) {
            isCurrent = false;
            log.error("name error");
        }

        if (gender != Gender.FEMALE) {
            if (gender != Gender.MALE) {
                isCurrent = false;
                log.error("gender error");
            }
        }
        if (phone != null) {
            if (!phone.matches(Regex.PHONE.getRegexString())) {
                isCurrent = false;
                log.error("phone error");
            }
        }
        if (birthday != null) {
            if (!(birthday.toString().matches(Regex.BIRTHDAY.getRegexString()))) {
                isCurrent = false;
                log.error("birthday error");
                log.error(memberRequest.getBirthday().toString());
                log.error(birthday.toString());
            }
        }
        return isCurrent;
    }
}
