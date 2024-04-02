package server.cps.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
