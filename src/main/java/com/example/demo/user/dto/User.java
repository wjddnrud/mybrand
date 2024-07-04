package com.example.demo.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class User {

    private String userName;
    private String userId;
    private String userPwd;
    private String userPhoneNumber;
    private String userEmail;
    private String userAddress;
    private String userGender;
    private String userBirthday;
    private int userAge;

}
