package com.steven.demoshop.model;

import com.steven.demoshop.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Integer memberId;
    private String memberEmail;
    private String password;
    private String memberName;
    private Gender gender;
    private Date birthday;
    private String address;
    private String phone;
    private Date createTime;
    private Date modifyTime;
}
