package com.hack.bundda.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetMember {
    private Long id;
    private String name;
    private String phoneNumber;
    private String gender;
    private String age;
    private String thumbnail;
}
