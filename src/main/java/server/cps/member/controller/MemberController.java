package server.cps.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.cps.entity.Member;
import server.cps.member.dto.MemberRequestDTO;
import server.cps.member.service.MemberSevice;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MemberController {

    private final MemberSevice memberSevice;
    @PostMapping("/api/member")
    public Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)
    {
        return memberSevice.createMember(memberRequestDTO);
    }
    @GetMapping("/api/members")
    public List<Member> showMembers(){
        return memberSevice.findAllMember();
    }


}
