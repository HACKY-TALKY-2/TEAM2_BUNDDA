package com.hack.bundda.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class NewMember {
    private String name;
    private String phoneNumber;
    private String gender;
    private String age;
    private MultipartFile thumbnail;
}
