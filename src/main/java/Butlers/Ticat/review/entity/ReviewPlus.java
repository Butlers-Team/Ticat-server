package Butlers.Ticat.review.entity;

import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewPlus {
    // 식별자
    private long reviewId;

    // 회원
    private Member member;

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
    private int likedCount = 0;

    // 비추천
    private int dislikedCount = 0;

    // 리뷰 생성 날짜, 시간
    private LocalDateTime createdAt = null;

    // 리뷰 수정 날짜, 시간
    private LocalDateTime modifiedAt = null;

    // 추천 여부
    private boolean liked;

    // 비추천 여부
    private boolean disliked;

    public ReviewPlus(long reviewId, Member member, String content, double rate, List<String[]> pictures,
                      List<ReviewComment> comments, int commentCount, int likedCount, int dislikedCount,
                      LocalDateTime createdAt, LocalDateTime modifiedAt, boolean liked, boolean disliked) {
        this.reviewId = reviewId;
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

    // Constructor without review comments and recommendation data
    public ReviewPlus(long reviewId, String content, double rate, List<String[]> pictures) {
        this.reviewId = reviewId;
        this.content = content;
        this.rate = rate;
        this.pictures = pictures;
    }
}
