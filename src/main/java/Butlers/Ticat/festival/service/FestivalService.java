package Butlers.Ticat.festival.service;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Favorite;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.repository.MemberRepository;
import Butlers.Ticat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final MemberService memberService;

    private final MemberRepository memberRepository;

    // 전체 리스트
    public Page<Festival> findFestivals(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size,Sort.by("festivalId").descending()));
    }

    // 위도 경도 거리에 따른 축제 찾기
    public List<Festival> findFestivalsWithinDistance(double latitude, double longitude, double distance) {
        return festivalRepository.findFestivalsWithinDistance(longitude,latitude, distance);
    }

    // 축제 상세 페이지
    public Festival findFestival(long festivalId) {
        Optional<Festival> optionalFestival = festivalRepository.findById(festivalId);
        Festival festival = optionalFestival.orElseThrow();
        return festival;
    }

    // 지역에 따라 축제 찾기
    public Page<Festival> findFestivalByArea(List<String> areas,int page,int size) {
        return festivalRepository.findByAreaIn(areas,PageRequest.of(page-1,size,Sort.by("festivalId").descending()));
    }

    // 메인 배너페이지
    public List<Festival> findFestivalByStatus(DetailFestival.Status status) {

        List<Festival> festivals = festivalRepository.findByDetailFestivalStatus(status);

        List<Festival> filteredFestivals = new ArrayList<>();

        for (Festival festival : festivals) {
            if (!festival.getImage().isEmpty()) {
                filteredFestivals.add(festival);
            }
        }

        if (filteredFestivals.size() >= 4) {
            Collections.shuffle(filteredFestivals);
            filteredFestivals = filteredFestivals.subList(0, 4);
        }

        return filteredFestivals;
    }

    // 카테고리와 지역 이용해서 축제 찾기
    public Page<Festival> findByCategoryAndArea(String category,List<String> areas,int page,int size) {
        return festivalRepository.findByDetailFestivalCategoryAndAreaIn(category,areas,PageRequest.of(page-1,size, Sort.by("festivalId").descending()));
    }

    // 카테고리 이용해서 축제 찾기
    public Page<Festival> findByCategory(String category,int page,int size) {
        return festivalRepository.findByDetailFestivalCategory(category,PageRequest.of(page-1,size,Sort.by("festivalId").descending()));
    }

    // 좋아요 하기
    public void createFavorite(long festivalId) {
        Festival festival = findFestival(festivalId);

        //로그인한 멤버 불러오기
        Member member = memberService.findMember(JwtParseInterceptor.getAuthenticatedMemberId());

        for(Favorite favorite : member.getFavorites()){
            if(favorite.getFestival().getFestivalId() == festival.getFestivalId()){
                throw new BusinessLogicException(ExceptionCode.LIKE_NOT_TWICE);
            }
        }

        festivalRepository.upFavorite(festival.getFestivalId(),member.getMemberId());

        festival.setLikeCount(festival.getLikeCount() + 1);
    }

    //좋아요 취소
    public void cancleFavorite(long festivalId) {
        Festival festival = findFestival(festivalId);

        //로그인한 멤버 불러오기
        Member member = memberService.findMember(JwtParseInterceptor.getAuthenticatedMemberId());

        festival.getFavorites().stream()
                .filter(f -> f.getMember() == member)
                .findFirst().orElseThrow(() -> new BusinessLogicException(ExceptionCode.LIKE_NOT_CANCEL));

        festivalRepository.downFavorite(festival.getFestivalId(),member.getMemberId());

        festival.setLikeCount(festival.getLikeCount() - 1);
    }


    //여러개의 카테고리로 축제 찾기 최신순
    public Page<Festival> findByCategories(List<String> categories ,int page,int size){
        return festivalRepository.findByDetailFestivalCategoryIn(categories, PageRequest.of(page-1,size,Sort.by("festivalId").descending()));
    }

    //여러개의 카테고리로 축제 찾기 좋아요순
    public Page<Festival> findByCategoriesAndSortByLikeCount(List<String> categories ,int page,int size){
        return festivalRepository.findByDetailFestivalCategoryIn(categories, PageRequest.of(page-1,size,Sort.by("likeCount").descending()));
    }

    //여러개의 카테고리로 축제 찾기 평점순
    public Page<Festival> findByCategoriesAndSortByReviewRating(List<String> categories ,int page,int size){
        return festivalRepository.findByDetailFestivalCategoryIn(categories, PageRequest.of(page-1,size,Sort.by("reviewRating").descending()));
    }

    //여러개의 카테고리로 축제 찾기 리뷰수순
    public Page<Festival> findByCategoriesAndSortByReviewCount(List<String> categories ,int page,int size){
        return festivalRepository.findByDetailFestivalCategoryIn(categories, PageRequest.of(page-1,size,Sort.by("reviewCount").descending()));
    }

    // 좋아요순 전체 리스트
    public Page<Festival> findFestivalsByLikeCount(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size,Sort.by("likeCount").descending()));
    }
    // 평점순 전체 리스트
    public Page<Festival> findFestivalsByReviewRating(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size,Sort.by("reviewRating").descending()));
    }
    // 리뷰수순 전체 리스트
    public Page<Festival> findFestivalsByReviewCount(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size,Sort.by("reviewCount").descending()));
    }

    public Page<Festival> findFestivalsByTitle(String title, int page, int size) {

        return festivalRepository.findByTitleContainingIgnoreCase(title,PageRequest.of(page -1, size));
    }
}
