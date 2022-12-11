package com.steven.demoshop.dto.member;

import com.steven.demoshop.constant.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class MemberModify {
    @Email
    String memberEmail;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$")
    String password;
    @Pattern(regexp = "[\\u4e00-\\u9fa5_a-zA-Z\\d]{3,20}")
    String memberName;
    Gender gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
    String address;
    @Pattern(regexp = "09\\d{2}-\\d{3}-\\d{3}")
    String phone;
}
