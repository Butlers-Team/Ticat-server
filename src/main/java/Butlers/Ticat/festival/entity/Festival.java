package Butlers.Ticat.festival.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "festival_id")
    private Long id;

    private String contentId; // 콘텐츠 아이디 ( 식별자 )
    private String title; // 축제 제목

    private String address; // 축제 주소

    private String areacode; // 축제 지역코드

    private String image; // 축제 사진

    private double mapx; // 축제 x좌표

    private double mapy; // 축제 y좌표

    private String tel; // 축제 전화번호

    @Embedded
    private DetailFestival detailFestival;

    public Festival(String contentId, String title, String address, String areacode, String image, double mapx, double mapy, String tel) {
        this.contentId = contentId;
        this.title = title;
        this.address = address;
        this.areacode = areacode;
        this.image = image;
        this.mapx = mapx;
        this.mapy = mapy;
        this.tel = tel;
    }

    public void updateDetailFestival(DetailFestival detailFestival) { this.detailFestival = detailFestival; }
}
