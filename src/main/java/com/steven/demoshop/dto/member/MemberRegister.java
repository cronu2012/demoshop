package com.steven.demoshop.dto.member;


import com.steven.demoshop.constant.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import java.time.LocalDate;


@Data
public class MemberRegister {
    Integer memberId;
    @NotBlank
    @Email
    String memberEmail;
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$")
    String password;
    @NotBlank
    @Pattern(regexp = "[\\u4e00-\\u9fa5_a-zA-Z\\d]{3,20}")
    String memberName;
    @NotNull
    Gender gender;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
    @NotNull
    String address;
    @NotBlank
    @Pattern(regexp = "09\\d{2}-\\d{3}-\\d{3}")
    String phone;
}
