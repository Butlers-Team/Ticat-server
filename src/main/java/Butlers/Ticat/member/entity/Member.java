package Butlers.Ticat.member.entity;

import Butlers.Ticat.calendar.entity.Calendar;
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
    private List<Calendar> calendars = new ArrayList<>();
    public void addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
    }
}
