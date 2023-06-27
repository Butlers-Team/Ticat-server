package Butlers.Ticat.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class DetailFestival {

    private String sponsor1; // 주최자 정보

    private String sponsor1tel; // 주최자 연락처

    private String sponsor2; // 주관사 정보

    private String eventstartdate; // 행사시작일

    private String eventenddate; // 행사 종료일

    private String playtime; //공연시간

    private String eventplace; // 행사장소

    private String usetimefestival; // 이용요금
}
