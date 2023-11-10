package com.hack.bundda.member;

import com.hack.bundda.global.Result;
import com.hack.bundda.member.domain.Member;
import com.hack.bundda.member.dto.GetMember;
import com.hack.bundda.member.dto.NewMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 허용할 Origin (클라이언트 주소)
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public Result create(NewMember newMember) {
        return memberService.create(newMember);
    }

    @GetMapping("/members")
    public List<Member> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/member/{id}")
    public Object get(@PathVariable("id") Long id) {
        try{
            return memberService.getMember(id);
        } catch (RuntimeException e) {
            return Result.builder().ok(false).message(e.getMessage()).build();
        }
    }

    @GetMapping("/member/check")
    public Result isDuplicateName(@RequestParam String name) {
        return memberService.isDuplicateName(name);
    }
}
