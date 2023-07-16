package Butlers.Ticat.review.entity;

import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ReviewComment {
    // 식별
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewCommentId;

    // 리뷰 N:1 매핑
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    // 회원 N:1 매핑
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 덧글 내용
    private String content;

    // 회원 설정 메서드
    public void setMember(Member member) {
        this.member = member;
        if (!member.getReviewComments().contains(this)) {
            member.addReviewComment(this);
        }
    }

    // 리뷰 설정 메서드
    public void setReview(Review review) {
        this.review = review;
        if (!review.getComments().contains(this)) {
            review.addComment(this);
        }
    }
}
