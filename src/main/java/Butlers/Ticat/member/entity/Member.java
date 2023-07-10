package Butlers.Ticat.member.entity;


import Butlers.Ticat.calendar.entity.Calendar;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;


import Butlers.Ticat.stamp.entity.Stamp;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

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

    // 로그인 아이디
    private String id;
    private String email;
    private String password;
    private String displayName;


    @OneToMany(mappedBy = "member")
    private List<Calendar> calendars = new ArrayList<>();
    public void addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
    }

    @OneToMany(mappedBy = "member")
    private List<Stamp> stampList = new ArrayList<>();
    public void addStamp(Stamp stamp) {
        this.stampList.add(stamp);
    }

    // 소셜 로그인 여부
    private boolean isOauthChecked;

    // 관심사 1:1 매핑
    @OneToOne(mappedBy = "member")
    private Interest interest;

    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites;

    // 프로필 이미지 (외부 접근용)
    private String profileUrl;

    // 프로필 이미지 순수 url (내부에서 접근용 // 수정, 삭제 등)
    private String pureProfileUrl;

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
