package server.cps.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.cps.entity.Member;
import server.cps.member.service.MemberSevice;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MemberController {

    private final MemberSevice memberSevice;

    @GetMapping("/api/members")
    public List<Member> showMembers(){
        return memberSevice.findAllMember();
    }

    @GetMapping("/api/member/{memberId}")
    public Member showMembers(@PathVariable Long memberId ){
        return memberSevice.findMember(memberId);
    }

}
