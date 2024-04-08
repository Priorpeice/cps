package server.cps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import server.cps.entity.userRole.UserRole;

// 역할 생성을 위함
@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Role {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Role(Member member){
        this.userRole= UserRole.USER;
        this.member = member;
    }
}
