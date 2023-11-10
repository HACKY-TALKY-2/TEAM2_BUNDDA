package com.hack.bundda.member.domain;

import com.hack.bundda.member.dto.NewMember;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String phoneNumber;
    private String gender;
    private String age;
    private String thumbnail;
    private String sessionId;
    private String chatRoom;

    public static Member createMember(NewMember newMember, String thumbnailUrl){
        Member member = new Member();
        member.setName(newMember.getName());
        member.setPhoneNumber(newMember.getPhoneNumber());
        member.setGender(newMember.getGender());
        member.setAge(newMember.getAge());
        member.setThumbnail(thumbnailUrl);
        return member;
    }
}
