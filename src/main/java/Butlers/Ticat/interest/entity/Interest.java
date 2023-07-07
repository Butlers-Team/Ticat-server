package Butlers.Ticat.interest.entity;

import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Interest {

    // 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    // 회원 1:1 매핑
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 관심사 목록
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> interests = new ArrayList<>();
}
