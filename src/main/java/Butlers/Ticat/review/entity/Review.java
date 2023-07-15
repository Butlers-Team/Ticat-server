package Butlers.Ticat.review.entity;

import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review {
    // 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    // 회원 N:1 매핑
    @ManyToOne
    private Member member;

    // 축제 N:1 매핑
    @ManyToOne
    private Festival festival;

    // 리뷰 내용
    private String content;

    // 별점
    private double rate;

    // 리뷰 이미지 S3 주소
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String[]> pictures = new ArrayList<>();
}
