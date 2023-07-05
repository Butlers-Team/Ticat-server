package Butlers.Ticat.member.entity;

import Butlers.Ticat.stamp.entity.Stamp;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "member")
    private List<Stamp> stampList = new ArrayList<>();
    public void addStamp(Stamp stamp) {
        this.stampList.add(stamp);
    }

}
