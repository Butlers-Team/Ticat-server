package Butlers.Ticat.festival.service;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Favorite;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.helper.AreaConverter;
import Butlers.Ticat.festival.repository.FavoriteRepository;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.repository.MemberRepository;
import Butlers.Ticat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static Butlers.Ticat.festival.entity.DetailFestival.Status.ONGOING;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {
    private final FestivalRepository festivalRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    // 위도 경도 거리에 따른 축제 찾기
    @Transactional(readOnly = true)
    public List<Festival> findFestivalsWithinDistance(double latitude, double longitude, double distance) {
        return festivalRepository.findFestivalsWithinDistance(longitude,latitude, distance);
    }

    // 축제 상세 페이지
    @Transactional(readOnly = true)
    public Festival findFestival(long festivalId) {
        Optional<Festival> optionalFestival = festivalRepository.findById(festivalId);
        Festival festival = optionalFestival.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FESTIVAL_NOT_FOUND));

        return festival;
    }

    // 메인 배너페이지
    @Transactional(readOnly = true)
    public List<Festival> findFestivalByStatus() {

        List<Festival> festivals = festivalRepository.findByDetailFestivalStatus(ONGOING);

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

    // 상세페이지 축제 추천
    @Transactional(readOnly = true)
    public List<Festival> findDetailRecommend(String category) {
        List<Festival> festivals = festivalRepository.findByDetailFestivalCategoryAndDetailFestivalStatus(category,ONGOING);

        List<Festival> filteredFestivals = new ArrayList<>();

        for (Festival festival : festivals) {
            if (!festival.getImage().isEmpty()) {
                filteredFestivals.add(festival);
            }
        }

        if (filteredFestivals.size() >= 5) {
            Collections.shuffle(filteredFestivals);
            filteredFestivals = filteredFestivals.subList(0, 5);
        }

        return filteredFestivals;
    }

    // 메인페이지 회원 관심사로 축제 추천
    @Transactional(readOnly = true)
    public List<Festival> findMainRecommend() {
        try{
            //로그인한 멤버 불러오기
            Member member = memberService.findMember(JwtParseInterceptor.getAuthenticatedMemberId());

            List<Festival> festivals = festivalRepository.findByDetailFestivalCategoryInAndDetailFestivalStatus(member.getInterest().getCategories(),ONGOING);

            List<Festival> filteredFestivals = new ArrayList<>();

            for (Festival festival : festivals) {
                if (!festival.getImage().isEmpty()) {
                    filteredFestivals.add(festival);
                }
            }

            if (filteredFestivals.size() >= 6) {
                Collections.shuffle(filteredFestivals);
                filteredFestivals = filteredFestivals.subList(0, 6);
            }

            return filteredFestivals;
        }catch (Exception e){
            List<Festival> festivals = festivalRepository.findByDetailFestivalStatus(ONGOING,Sort.by("likeCount").descending());

            List<Festival> filteredFestivals = new ArrayList<>();

            for (Festival festival : festivals) {
                if (!festival.getImage().isEmpty()) {
                    filteredFestivals.add(festival);
                }
            }

            if (filteredFestivals.size() >= 6) {
                filteredFestivals = filteredFestivals.subList(0, 6);
            }

            return filteredFestivals;
        }
    }

    //축제 리스트 필터
    @Transactional(readOnly = true)
    public Page<Festival> getFilteredFestivalList(String category, List<String> areas, int page, int size) {
        List<String> areaList = new ArrayList<>();
        if(areas != null) areaList = AreaConverter.convertToSpecialCity(areas);
        Sort sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "festivalId");

        if (category == null || category.equalsIgnoreCase("전체")) {
            if (areas != null && !areas.isEmpty()) {
                return festivalRepository.findByAreaIn(areaList, PageRequest.of(page - 1, size, sort));
            } else {
                return festivalRepository.findAll(PageRequest.of(page - 1, size, sort));
            }
        } else {
            if (areas != null && !areas.isEmpty()) {
                return festivalRepository.findByDetailFestivalCategoryAndAreaIn(category, areaList, PageRequest.of(page - 1, size, sort));
            } else {
                return festivalRepository.findByDetailFestivalCategory(category, PageRequest.of(page - 1, size, sort));
            }
        }
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

    // 좋아요 했는지 안했는지 체크
    public FestivalDto.Response isFestivalLiked(FestivalDto.Response festival){
        try{
            long memberId = JwtParseInterceptor.getAuthenticatedMemberId();
            boolean isLiked = isFestivalLikedByMember(festival.getFestivalId(), memberId);
            festival.setLiked(isLiked);
            return festival;
        }catch (Exception e){
            return festival;
        }
    }

    // Favorite 엔티티에서 festivalId와 memberId를 기준으로 조회하여 좋아요 여부를 확인하는 로직
    public boolean isFestivalLikedByMember(long festivalId, long memberId) {
        Optional<Favorite> favorite = favoriteRepository.findByFestivalFestivalIdAndMemberMemberId(festivalId, memberId);
        return favorite.isPresent();
    }

    @Transactional(readOnly = true)
    public Page<Festival> getFilteredFestivals(double longitude, double latitude, List<String> categories, String sortBy, List<DetailFestival.Status> status, int page, int size) {
        Sort sort;
        if (sortBy == null) {
            sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "festivalId");
        } else {
            switch (sortBy) {
                case "likeCount":
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "likeCount");
                    break;
                case "reviewRating":
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "reviewRating");
                    break;
                case "reviewCount":
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "reviewCount");
                    break;
                default:
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "festivalId");
                    break;
            }
        }

        if (categories != null && !categories.isEmpty()) {
            return festivalRepository.findFestivalsWithinDistanceAndCategoryInAndDetailFestivalStatusIn(latitude,longitude,3000.0,categories,status ,PageRequest.of(page - 1, size, sort));
        } else {
            return festivalRepository.findFestivalsWithinDistanceAndDetailFestivalStatusIn(latitude,longitude,3000.0,status,PageRequest.of(page - 1, size, sort));
        }
    }

    @Transactional(readOnly = true)
    public Page<Festival> findByKeywordAndAreas(String keyword, List<String> categories,String sortBy,List<DetailFestival.Status> status, int page, int size) {
        Sort sort;
        if (sortBy == null) {
            sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "festivalId");
        } else {
            switch (sortBy) {
                case "likeCount":
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "likeCount");
                    break;
                case "reviewRating":
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "reviewRating");
                    break;
                case "reviewCount":
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "reviewCount");
                    break;
                default:
                    sort = Sort.by(Sort.Direction.DESC, "detailFestival.status", "festivalId");
                    break;
            }
        }

        if (keyword != null && !keyword.isEmpty() && categories != null && !categories.isEmpty()) {
            return festivalRepository.findByKeywordAndCategoryInAndDetailFestivalStatus(keyword, categories, status,PageRequest.of(page - 1, size, sort));
        } else if (keyword != null && !keyword.isEmpty()) {
            return festivalRepository.findByTitleOrAreaContainingIgnoreCaseAndDetailFestivalStatus(keyword,status, PageRequest.of(page - 1, size, sort));
        } else if (categories != null && !categories.isEmpty()) {
            return festivalRepository.findByDetailFestivalCategoryInAndDetailFestivalStatusIn(categories,status, PageRequest.of(page - 1, size, sort));
        } else {
            return festivalRepository.findByDetailFestivalStatusIn(status,PageRequest.of(page - 1, size, sort));
        }
    }

    @Transactional(readOnly = true)
    public Page<Festival> findFestivalsByTitle(String title, int page, int size) {
        return festivalRepository.findByTitleContainingIgnoreCase(title,PageRequest.of(page -1, size));
    }

    // 두 좌표 사이의 거리 계산하는 기능
    @Transactional(readOnly = true)
    public FestivalDto.DistanceResponse calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // 지구 반지름 (단위: km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        FestivalDto.DistanceResponse distanceResponse = new FestivalDto.DistanceResponse();
        distanceResponse.setKm(earthRadius * c);

        return distanceResponse;
    }
}
