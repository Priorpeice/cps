package server.cps.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponseDTO {
    private final String memberId;
    private final String nickname;
    private final String phone;
    private final String email;
    private final String name;
}
