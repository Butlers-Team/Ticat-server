package Butlers.Ticat.festival.helper;

import java.util.HashMap;
import java.util.Map;

public class KeywordCategoryMapping {
    public static final Map<String, String> keywordCategoryMap = new HashMap<>();

    static {
        // 음악 카테고리와 관련된 키워드
        keywordCategoryMap.put("음악", "음악");
        keywordCategoryMap.put("공연", "음악");
        keywordCategoryMap.put("라이브", "음악");
        keywordCategoryMap.put("콘서트", "음악");

        // 미술 카테고리와 관련된 키워드
        keywordCategoryMap.put("미술", "미술");
        keywordCategoryMap.put("전시", "미술");
        keywordCategoryMap.put("아트", "미술");

        // 영화 카테고리와 관련된 키워드
        keywordCategoryMap.put("영화", "영화");
        keywordCategoryMap.put("시네마", "영화");
        keywordCategoryMap.put("영화제", "영화");

        // 문화 카테고리와 관련된 키워드
        keywordCategoryMap.put("문화", "문화");
        keywordCategoryMap.put("축제", "문화");

        // 국제 카테고리와 관련된 키워드
        keywordCategoryMap.put("국제", "국제");
        keywordCategoryMap.put("세계", "국제");
        keywordCategoryMap.put("인터내셔널", "국제");

        // 역사 카테고리와 관련된 키워드
        keywordCategoryMap.put("역사", "역사");
        keywordCategoryMap.put("문화유산", "역사");
        keywordCategoryMap.put("고고학", "역사");

        // 과학 카테고리와 관련된 키워드
        keywordCategoryMap.put("과학", "과학");
        keywordCategoryMap.put("기술", "과학");
        keywordCategoryMap.put("연구", "과학");

        // 스포츠 카테고리와 관련된 키워드
        keywordCategoryMap.put("스포츠", "스포츠");
        keywordCategoryMap.put("운동", "스포츠");
        keywordCategoryMap.put("경기", "스포츠");

        // 요리 카테고리와 관련된 키워드
        keywordCategoryMap.put("요리", "요리");
        keywordCategoryMap.put("음식", "요리");
        keywordCategoryMap.put("맛집", "요리");

        // 주류 카테고리와 관련된 키워드
        keywordCategoryMap.put("주류", "주류");
        keywordCategoryMap.put("와인", "주류");
        keywordCategoryMap.put("맥주", "주류");
        keywordCategoryMap.put("소주", "주류");
        keywordCategoryMap.put("보드카", "주류");

        // 정원 카테고리와 관련된 키워드
        keywordCategoryMap.put("정원", "정원");
        keywordCategoryMap.put("꽃", "정원");
        keywordCategoryMap.put("조경", "정원");

        // 종교 카테고리와 관련된 키워드
        keywordCategoryMap.put("종교", "종교");
        keywordCategoryMap.put("신앙", "종교");
        keywordCategoryMap.put("성지", "종교");

        // 전통 카테고리와 관련된 키워드
        keywordCategoryMap.put("전통", "전통");
        keywordCategoryMap.put("문화재", "전통");
        keywordCategoryMap.put("민속", "전통");
    }

    public static String determineCategory(String overview, Map<String, String> keywordCategoryMap) {
        for (Map.Entry<String, String> entry : keywordCategoryMap.entrySet()) {
            String keyword = entry.getKey();
            String category = entry.getValue();
            if (overview.contains(keyword)) {
                return category;
            }
        }
        return "기타";
    }

}
