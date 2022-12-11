package com.steven.demoshop.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberLogin {
    @NotBlank
    @Email
    private String memberEmail;
    @NotBlank
    private String password;
}
