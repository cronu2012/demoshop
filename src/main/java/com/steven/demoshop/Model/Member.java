package com.steven.demoshop.Model;

import com.steven.demoshop.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Integer memberId;
    private String memberEmail;
    private Gender gender;
    private LocalDate birthday;
    private String address;
    private String phone;
    private LocalTime createTime;
    private LocalTime modifyTime;
}
