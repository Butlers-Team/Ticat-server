package Butlers.Ticat.festival.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Embeddable
@Getter
public class DetailFestival {

    private String category;
    private String sponsor1; // 주최자 정보

    private String sponsor1tel; // 주최자 연락처

    private String sponsor2; // 주관사 정보
    private String eventstartdate; // 행사시작일

    private String eventenddate; // 행사 종료일

    @Column(length = 1000)
    private String eventhomepage; // 홈페이지

    private String playtime; //공연시간

    private String eventplace; // 행사장소

    @Column(length = 1000)
    private String usetimefestival; // 이용요금

    @Column(length = 2000)
    private String overview; // 상세 정보

    @Enumerated(EnumType.STRING)
    private Status status;

    public void updateStatus() {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(LocalDate.parse(eventstartdate, DateTimeFormatter.ofPattern("yyyyMMdd")))) {
            status = Status.PLANNED;
        } else if (currentDate.isEqual(LocalDate.parse(eventstartdate,DateTimeFormatter.ofPattern("yyyyMMdd"))) || currentDate.isBefore(LocalDate.parse(eventenddate,DateTimeFormatter.ofPattern("yyyyMMdd")))) {
            status = Status.ONGOING;
        } else {
            status = Status.COMPLETED;
        }
    }

    public DetailFestival(String sponsor1, String sponsor1tel, String sponsor2, String eventstartdate, String eventenddate, String eventhomepage, String playtime, String eventplace, String usetimefestival) {
        this.sponsor1 = sponsor1;
        this.sponsor1tel = sponsor1tel;
        this.sponsor2 = sponsor2;
        this.eventstartdate = eventstartdate;
        this.eventenddate = eventenddate;
        this.eventhomepage = eventhomepage;
        this.playtime = playtime;
        this.eventplace = eventplace;
        this.usetimefestival = usetimefestival;
    }

    public void updateCategoryAndOverView(String category, String overview) {
        this.category = category;
        this.overview = overview;
    }


    public enum Status {
        PLANNED("진행 예정"),
        ONGOING("진행 중"),
        COMPLETED("진행 종료");

        private String description;

        Status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
