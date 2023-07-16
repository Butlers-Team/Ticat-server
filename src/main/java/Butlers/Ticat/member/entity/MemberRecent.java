package Butlers.Ticat.member.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRecent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRecentId;

    private Long festivalId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberRecent(Long festivalId) {
        this.festivalId = festivalId;
    }

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMemberRecentList().contains(this)) {
            member.getMemberRecentList().add(this);
        }
    }

}
