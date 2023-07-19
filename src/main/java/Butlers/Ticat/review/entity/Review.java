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

    // 리뷰 댓글 1:N 매핑
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ReviewComment> comments = new ArrayList<>();

    // 리뷰 댓글 수
    private int commentCount = 0;

    // 추천
    private int liked = 0;

    // 비추천
    private int disliked = 0;

    // 리뷰 추천 1:N 매핑
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ReviewRecommend> reviewRecommends = new ArrayList<>();

    // 회원 설정 메서드
    public void setMember(Member member) {
        this.member = member;
        if (!member.getReviews().contains(this)) {
            member.addReview(this);
        }
    }

    // 리뷰 댓글 추가 메서드
    public void addComment(ReviewComment reviewComment) {
        this.comments.add(reviewComment);
        if (reviewComment.getReview() != this) {
            reviewComment.setReview(this);
        }
    }

    // 리뷰 추천 추가 매서드
    public void addRecommend(ReviewRecommend recommend) {
        this.reviewRecommends.add(recommend);
        if (recommend.getReview() != this) {
            recommend.setReview(this);
        }
    }
}
