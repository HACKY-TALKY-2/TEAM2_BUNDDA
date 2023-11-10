package com.hack.bundda.member;

import com.hack.bundda.aws.S3;
import com.hack.bundda.global.Result;
import com.hack.bundda.member.domain.Member;
import com.hack.bundda.member.dto.GetMember;
import com.hack.bundda.member.dto.NewMember;
import com.hack.bundda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final S3 s3;
    private final MemberRepository memberRepository;
    public Result create(NewMember newMember) {
        Result result;
        try {
            // 새로운 멤버를 생성하고, S3에 업로드한 후, DB에 저장한다.
            String thumbnailUrl = s3.uploadFile(newMember.getThumbnail(), newMember.getName());
            Member member = Member.createMember(newMember, thumbnailUrl);
            memberRepository.save(member);
            result = Result.builder().message(member.getId().toString()).ok(true).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            result = Result.builder().message(e.getMessage()).ok(false).build();
        }
        return result;
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    public GetMember getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
        return GetMember.builder()
                .age(member.getAge())
                .gender(member.getGender())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .thumbnail(member.getThumbnail())
                .id(member.getId())
                .build();
    }

    public Result isDuplicateName(String name) {
        Result result;
        try {
            Member member = memberRepository.findByName(name);
            if (member == null) {
                result = Result.builder().message("success").ok(true).build();
            } else {
                result = Result.builder().message("duplicate name").ok(false).build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            result = Result.builder().message(e.getMessage()).ok(false).build();
        }
        return result;
    }

    public void updateSessionId(String id, String sessionId){
        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow();
        member.setSessionId(sessionId);
        memberRepository.save(member);
    }

    public void updateChatRoom(String id, String sessionId){
        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow();
        member.setSessionId(sessionId);
        memberRepository.save(member);
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(Long.valueOf(id));
    }

    public void closeChatRoom(String id) {
        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow();
        member.setChatRoom(null);
        memberRepository.save(member);
    }
}
