package Butlers.Ticat.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    // 오어스 회원가입을 위한 생성자
    public Member(String email) {
        this.email = email;
    }
}
