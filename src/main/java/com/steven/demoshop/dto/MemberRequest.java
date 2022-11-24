package com.steven.demoshop.dto;


import com.steven.demoshop.constant.Gender;
import lombok.Data;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@Value
public class MemberRequest {
    Integer memberId;
    @NotBlank
    String memberEmail;
    @NotBlank
    String password;
    @NotBlank
    String memberName;
    @NotBlank
    Gender gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
    String address;
    String phone;
}
