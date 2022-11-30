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

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid MemberRequest memberRequest
    ) {
        Member oldMember = memberService.getMember(id);
        if (oldMember == null) {
            log.error("member not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            String email = memberRequest.getMemberEmail();
            String password = memberRequest.getPassword();
            String name = memberRequest.getMemberName();
            Gender gender = memberRequest.getGender();
            LocalDate birthday = memberRequest.getBirthday();
            String address = memberRequest.getAddress();
            String phone = memberRequest.getPhone();

            boolean isCurrent = checkMemberParam(memberRequest);

            if (!oldMember.getMemberEmail().equals(email)) {
                List<Member> list = memberService.getAll();
                for (Member m : list) {
                    if (m.getMemberEmail().equals(email))
                        log.error("email already created");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }

            if (!isCurrent) {
                log.error("parameter error");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
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

                Integer resultId = memberService.insertOrUpdate(member);
                Member result = memberService.getMember(resultId);
                if (result == null) {
                    log.error("update failed");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                } else {
                    result.setPassword("*****");
                    log.info("update success");
                    return ResponseEntity.status(HttpStatus.OK).body(result);
                }
            }
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable @Min(1) Integer id) {
        Member member = memberService.getMember(id);

        if (member != null) {
            member.setPassword("*****");
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            log.error("member null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@RequestParam(required = false) String email) {
        if (email == null) {
            List<Member> members = memberService.getAll();
            if (members == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                for (Member m : members) {
                    m.setPassword("*****");
                }
                return ResponseEntity.status(HttpStatus.OK).body(members);
            }
        }
        if (!(email.matches(Regex.EMAIL.getRegexString()))) {
            log.error("email error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Member member = memberService.getMember(email);

        if (member != null) {
            member.setPassword("*****");
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/members")
    public ResponseEntity<Member> insert(@RequestBody @Valid MemberRequest memberRequest) {
        boolean isCurrent = checkMemberParam(memberRequest);
        String email = memberRequest.getMemberEmail();
        String password = memberRequest.getPassword();
        String name = memberRequest.getMemberName();
        Gender gender = memberRequest.getGender();
        LocalDate birthday = memberRequest.getBirthday();
        String address = memberRequest.getAddress();
        String phone = memberRequest.getPhone();

        Member memberByEmail = memberService.getMember(email);
        if (memberByEmail != null) {
            log.error("email already created");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        if (!isCurrent) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Member member = Member.builder()
                    .memberEmail(email)
                    .password(password)
                    .memberName(name)
                    .birthday(birthday)
                    .gender(gender)
                    .address(address)
                    .phone(phone)
                    .build();
            Integer id = memberService.insertOrUpdate(member);
            Member result = memberService.getMember(id);

            if (result == null) {
                log.error("creation failed");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                result.setPassword("*****");
                log.info("creation success");
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
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
