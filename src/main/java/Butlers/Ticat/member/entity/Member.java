package Butlers.Ticat.member.entity;

import Butlers.Ticat.festival.entity.Favorite;
import Butlers.Ticat.interest.entity.Interest;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String password;
    private String displayName;

    // 소셜 로그인 여부
    private boolean isOauthChecked;

    // 관심사 1:1 매핑
    @OneToOne(mappedBy = "member")
    private Interest interest;

    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites;

    // 관심사 설정 메서드
    public void setInterest(Interest interest) {
        this.interest = interest;
        if (interest.getMember() != this) {
            interest.setMember(this);
        }
    }

    // 오어스 회원가입을 위한 생성자
    public Member(String email) {
        this.email = email;
    }
}
