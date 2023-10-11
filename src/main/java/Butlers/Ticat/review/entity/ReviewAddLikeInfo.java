package Butlers.Ticat.review.entity;

import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// 토큰에 들어온 어느 회원에 대해 어느 리뷰에 대한 추천 여부를 확인하기 위해 생성
@Getter
@Setter
public class ReviewAddLikeInfo extends Review{
    // 추천 여부
    private boolean liked;

    // 비추천 여부
    private boolean disliked;

    public ReviewAddLikeInfo(long reviewId, Festival festival, Member member, String content, double rate, List<String[]> pictures,
                             List<ReviewComment> comments, int commentCount, int likedCount, int dislikedCount,
                             LocalDateTime createdAt, LocalDateTime modifiedAt, boolean liked, boolean disliked) {
        super.setReviewId(reviewId);
        super.setFestival(festival);
        super.setMember(member);
        super.setContent(content);
        super.setRate(rate);
        super.setPictures(pictures);
        super.setComments(comments);
        super.setCommentCount(commentCount);
        super.setLikedCount(likedCount);
        super.setDislikedCount(dislikedCount);
        super.setCreatedAt(createdAt);
        super.setModifiedAt(modifiedAt);
        this.liked = liked;
        this.disliked = disliked;
    }
}
