package com.steven.demoshop.controller;

import com.steven.demoshop.dto.MemberRequest;
import com.steven.demoshop.model.Member;
import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController(value = "/api")
public class MemberController {

    @Autowired
    private MemberService memberService;

  
    @GetMapping("/v1/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable  Integer id){
        Member member = memberService.getOneById(id);

        if(member!=null){
            return ResponseEntity.status(HttpStatus.OK).body(member);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/v1/member")
    public ResponseEntity<Member> insert(@RequestBody @Valid MemberRequest memberRequest){

        return null;
    }



}
