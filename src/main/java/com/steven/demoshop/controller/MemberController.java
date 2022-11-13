package com.steven.demoshop.controller;

import com.steven.demoshop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;


}
