package com.steven.demoshop.controller;

import com.steven.demoshop.model.Member;
import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ResponseBody
    public ResponseEntity<Member> getMember(Integer id){
        Member member = memberService.getOneById(id);

        if(member!=null){
            return ResponseEntity.status(HttpStatus.OK).body(member);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
