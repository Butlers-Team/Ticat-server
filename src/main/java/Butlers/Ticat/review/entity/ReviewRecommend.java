package Butlers.Ticat.review.entity;

import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ReviewRecommend {
    // 생성자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewRecommendId;

    // 회원 N:1 매핑
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 리뷰 N:1 매핑
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    // 추천 상태 (좋아요)
    private boolean liked = false;

    // 추천 상태 (싫어요)
    private boolean disliked = false;

    // 회원 설정 메서드
    public void setMember(Member member) {
        this.member = member;
        if (!member.getReviewRecommends().contains(this)) {
            member.addRecommend(this);
        }
    }

    // 리뷰 설정 메서드
    public void setReview(Review review) {
        this.review = review;
        if (!review.getReviewRecommends().contains(this)) {
            review.addRecommend(this);
        }
    }
}
