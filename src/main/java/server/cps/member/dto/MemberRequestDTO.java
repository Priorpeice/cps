package server.cps.member.dto;

import lombok.Data;
import server.cps.entity.Login;
import server.cps.entity.Member;

@Data
public class MemberRequestDTO {
    private String id;
    private String pw;
    private String name;
    private String nickname;
    private String phone;
    private Long scode;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .nickname(this.nickname)
                .phone(this.phone)
                .studentId(this.scode)
                .email(this.email)
                .build();
    }
    public Login toEntity(Member member){
        return Login.builder()
                .id(this.id)
                .pw(this.pw)
                .member(member)
                .build();
    }


}
