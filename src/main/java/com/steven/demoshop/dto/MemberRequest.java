package com.steven.demoshop.dto;



import com.steven.demoshop.constant.Gender;
import lombok.Data;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;



@Data
public class MemberRequest {
    Integer memberId;
    @NotBlank
    String memberEmail;
    @NotBlank
    String password;
    @NotBlank
    String memberName;
    @NotNull
    Gender gender;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
    String address;
    @NotBlank
    String phone;
}
