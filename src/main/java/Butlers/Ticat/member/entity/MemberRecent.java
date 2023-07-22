package Butlers.Ticat.member.entity;

import Butlers.Ticat.festival.entity.Festival;
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    public MemberRecent(Festival festival) {
        this.festival = festival;
    }

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMemberRecentList().contains(this)) {
            member.getMemberRecentList().add(this);
        }
    }

}
