package Butlers.Ticat.review.entity;

import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReviewPlus {
    // 식별자
    private long reviewId;

    // 축제
    private Festival festival;

    // 회원
    private Member member;

    // 리뷰 내용
    private String content;

    // 별점
    private double rate;

    // 리뷰 이미지 S3 주소
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String[]> pictures;

    // 리뷰 댓글 1:N 매핑
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ReviewComment> comments;

    // 리뷰 댓글 수
    private int commentCount;

    // 추천
    private int likedCount;

    // 비추천
    private int dislikedCount;

    // 리뷰 생성 날짜, 시간
    private LocalDateTime createdAt;

    // 리뷰 수정 날짜, 시간
    private LocalDateTime modifiedAt;

    // 추천 여부
    private boolean liked;

    // 비추천 여부
    private boolean disliked;

    public ReviewPlus(long reviewId, Festival festival, Member member, String content, double rate, List<String[]> pictures,
                      List<ReviewComment> comments, int commentCount, int likedCount, int dislikedCount,
                      LocalDateTime createdAt, LocalDateTime modifiedAt, boolean liked, boolean disliked) {
        this.reviewId = reviewId;
        this.festival = festival;
        this.member = member;
        this.content = content;
        this.rate = rate;
        this.pictures = pictures;
        this.comments = comments;
        this.commentCount = commentCount;
        this.likedCount = likedCount;
        this.dislikedCount = dislikedCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.liked = liked;
        this.disliked = disliked;
    }
}
