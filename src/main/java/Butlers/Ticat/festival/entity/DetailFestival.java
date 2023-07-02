package Butlers.Ticat.festival.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
@Getter
public class DetailFestival {

    private String sponsor1; // 주최자 정보

    private String sponsor1tel; // 주최자 연락처

    private String sponsor2; // 주관사 정보

    private String eventDate;
//    private String eventstartdate; // 행사시작일
//
//    private String eventenddate; // 행사 종료일

    @Column(length = 1000)
    private String eventhomepage; // 홈페이지

    private String playtime; //공연시간

    private String eventplace; // 행사장소

    private String usetimefestival; // 이용요금

    @Column(length = 2000)
    private String overview; // 상세 정보

    public DetailFestival(String sponsor1, String sponsor1tel, String sponsor2, String eventDate, String eventhomepage, String playtime, String eventplace, String usetimefestival) {
        this.sponsor1 = sponsor1;
        this.sponsor1tel = sponsor1tel;
        this.sponsor2 = sponsor2;
        this.eventDate = eventDate;
        this.eventhomepage = eventhomepage;
        this.playtime = playtime;
        this.eventplace = eventplace;
        this.usetimefestival = usetimefestival;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
