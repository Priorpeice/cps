package server.cps.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import server.cps.common.CpsResponse;
import server.cps.common.ResponseBody;
import server.cps.common.Status;
import server.cps.entity.Member;
import server.cps.member.dto.MemberResponseDTO;
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

    @GetMapping("/api/member/details")
    public ResponseEntity<ResponseBody<MemberResponseDTO>> showDetailMember(@AuthenticationPrincipal UserDetails userDetails)
    {
        String memberId = userDetails.getUsername();
        Member member = memberSevice.findMemberWithLoginid(memberId);
        MemberResponseDTO memberResponseDTO= MemberResponseDTO.builder()
                .memberId(member.getId().toString())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
        return CpsResponse.toResponse(Status.READ,memberResponseDTO);
    }
}
