package Butlers.Ticat.festival.entity;


import Butlers.Ticat.calendar.entity.Calendar;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.stamp.entity.Stamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Festival {

    @Id
    @Column(name = "festival_id")
    private Long festivalId; //  축제 아이디
    private String title; // 축제 제목
    private String address; // 축제 주소
    private String area; // 축제 위치
    private String image; // 축제 사진
    private double mapx; // 축제 x좌표
    private double mapy; // 축제 y좌표
    private String tel; // 축제 전화번호
    private Integer likeCount = 0;
    private double reviewRating = 0;
    private Integer reviewCount = 0;
    @Embedded
    private DetailFestival detailFestival;
    @OneToMany(mappedBy = "festival",cascade = CascadeType.REMOVE)
    private List<Calendar> calendar = new ArrayList<>();
    @OneToMany(mappedBy = "festival",cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;
    @OneToMany(mappedBy = "festival",cascade = CascadeType.REMOVE)
    private List<Stamp> stamps = new ArrayList<>();
    @OneToMany(mappedBy = "festival",cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    public void addCalendar(Calendar calendar) {
        this.calendar.add(calendar);
    }
    public void addStamp(Stamp stamp) {
        this.stamps.add(stamp);
    }
    public void updateDetailFestival(DetailFestival detailFestival) { this.detailFestival = detailFestival; }

    public Festival(Long festivalId, String title, String address, String area, String image, double mapx, double mapy, String tel) {
        this.festivalId = festivalId;
        this.title = title;
        this.address = address;
        this.area = area;
        this.image = image;
        this.mapx = mapx;
        this.mapy = mapy;
        this.tel = tel;
    }
}
