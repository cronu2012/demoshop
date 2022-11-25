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

@Slf4j
@Validated
@RestController
@RequestMapping("/demoshop")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable @Min(1) Integer id) {
        Member member = memberService.getOneById(id);

        if (member != null) {
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            log.error("member null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/members")
    public ResponseEntity<Member> getMemberByEmail(@RequestParam String email){
        if (!(email.matches(Regex.EMAIL.getRegexString()))){
            log.error("email error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Member member = memberService.getOneByEmail(email);

        if(member!=null){
            return ResponseEntity.status(HttpStatus.OK).body(member);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/member")
    public ResponseEntity<Member> insert(@RequestBody @Valid MemberRequest memberRequest) {
        boolean isCurrent = true;
        String email = memberRequest.getMemberEmail();
        String password = memberRequest.getPassword();
        String name = memberRequest.getMemberName();
        Gender gender = memberRequest.getGender();
        LocalDate birthday = memberRequest.getBirthday();
        String address = memberRequest.getAddress();
        String phone = memberRequest.getPhone();

        Member memberByEmail = memberService.getOneByEmail(email);
        if (memberByEmail!=null){
            log.error("email already created");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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
            }
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
            Integer id = memberService.insert(member);
            Member result = memberService.getOneById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

    }


}
