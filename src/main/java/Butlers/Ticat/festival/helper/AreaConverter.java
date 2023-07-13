package Butlers.Ticat.festival.helper;

import java.util.ArrayList;
import java.util.List;

public class AreaConverter {
    public static List<String> convertToSpecialCity(List<String> areas) {
        List<String> convertedAreas = new ArrayList<>();
        for (String area : areas) {
            if (area.startsWith("(서울)") && !area.equals("(서울)")) {
                convertedAreas.add("서울특별시 " + area.substring(4));
            }else if(area.startsWith("(인천)") && !area.equals("(인천)")) {
                convertedAreas.add("인천광역시 " + area.substring(4));
            }else if(area.startsWith("(대전)") && !area.equals("(대전)")) {
                convertedAreas.add("대전광역시 " + area.substring(4));
            }else if(area.startsWith("(광주)") && !area.equals("(광주)")) {
                convertedAreas.add("광주광역시 " + area.substring(4));
            }else if(area.startsWith("(부산)") && !area.equals("(부산)")) {
                convertedAreas.add("부산광역시 " + area.substring(4));
            }else if(area.startsWith("(울산)") && !area.equals("(울산)")) {
                convertedAreas.add("울산광역시 " + area.substring(4));
            }else if(area.startsWith("(경기)") && !area.equals("(경기)")) {
                convertedAreas.add("경기도 " + area.substring(4));
            }else if(area.startsWith("(강원)") && !area.equals("(강원)")) {
                convertedAreas.add("강원특별자치도 " + area.substring(4));
            }else if(area.startsWith("(충북)") && !area.equals("(충북)")) {
                convertedAreas.add("충청북도 " + area.substring(4));
            }else if(area.startsWith("(충남)") && !area.equals("(충남)")) {
                convertedAreas.add("충청남도 " + area.substring(4));
            }else if(area.startsWith("(경북)") && !area.equals("(경북)")) {
                convertedAreas.add("경상북도 " + area.substring(4));
            }else if(area.startsWith("(경남)") && !area.equals("(경남)")) {
                convertedAreas.add("경상남도 " + area.substring(4));
            }else if(area.startsWith("(전북)") && !area.equals("(전북)")) {
                convertedAreas.add("전라북도 " + area.substring(4));
            }else if(area.startsWith("(전남)") && !area.equals("(전남)")) {
                convertedAreas.add("전라남도 " + area.substring(4));
            }else if(area.startsWith("(제주)") && !area.equals("(제주)")) {
                convertedAreas.add("제주도 " + area.substring(4));
            }else if(area.equals("(서울)")){
                convertedAreas.add("서울특별시 강남구");
                convertedAreas.add("서울특별시 강동구");
                convertedAreas.add("서울특별시 강북구");
                convertedAreas.add("서울특별시 강서구");
                convertedAreas.add("서울특별시 관악구");
                convertedAreas.add("서울특별시 광진구");
                convertedAreas.add("서울특별시 구로구");
                convertedAreas.add("서울특별시 금천구");
                convertedAreas.add("서울특별시 노원구");
                convertedAreas.add("서울특별시 도봉구");
                convertedAreas.add("서울특별시 동대문구");
                convertedAreas.add("서울특별시 동작구");
                convertedAreas.add("서울특별시 마포구");
                convertedAreas.add("서울특별시 서대문구");
                convertedAreas.add("서울특별시 서초구");
                convertedAreas.add("서울특별시 성동구");
                convertedAreas.add("서울특별시 성북구");
                convertedAreas.add("서울특별시 송파구");
                convertedAreas.add("서울특별시 양천구");
                convertedAreas.add("서울특별시 영등포구");
                convertedAreas.add("서울특별시 용산구");
                convertedAreas.add("서울특별시 은평구");
                convertedAreas.add("서울특별시 종로구");
                convertedAreas.add("서울특별시 중구");
                convertedAreas.add("서울특별시 중랑구");
            }else if(area.equals("(인천)")){
                convertedAreas.add("인천광역시 강화군");
                convertedAreas.add("인천광역시 계양구");
                convertedAreas.add("인천광역시 미추홀구");
                convertedAreas.add("인천광역시 남동구");
                convertedAreas.add("인천광역시 동구");
                convertedAreas.add("인천광역시 부평구");
                convertedAreas.add("인천광역시 서구");
                convertedAreas.add("인천광역시 연수구");
                convertedAreas.add("인천광역시 웅진군");
                convertedAreas.add("인천광역시 중구");
            }else if(area.equals("(대전)")){
                convertedAreas.add("대전광역시 대덕구");
                convertedAreas.add("대전광역시 동구");
                convertedAreas.add("대전광역시 서구");
                convertedAreas.add("대전광역시 유성구");
                convertedAreas.add("대전광역시 중구");
            }else if(area.equals("(대구)")){
                convertedAreas.add("대구광역시 남구");
                convertedAreas.add("대구광역시 달서구");
                convertedAreas.add("대구광역시 달성군");
                convertedAreas.add("대구광역시 동구");
                convertedAreas.add("대구광역시 북구");
                convertedAreas.add("대구광역시 서구");
                convertedAreas.add("대구광역시 수성구");
                convertedAreas.add("대구광역시 중구");
            }else if(area.equals("(광주)")){
                convertedAreas.add("광주광역시 광산구");
                convertedAreas.add("광주광역시 남구");
                convertedAreas.add("광주광역시 동구");
                convertedAreas.add("광주광역시 북구");
                convertedAreas.add("광주광역시 서구");
            }else if(area.equals("(부산)")){
                convertedAreas.add("부산광역시 강서구");
                convertedAreas.add("부산광역시 금정구");
                convertedAreas.add("부산광역시 기장군");
                convertedAreas.add("부산광역시 남구");
                convertedAreas.add("부산광역시 동구");
                convertedAreas.add("부산광역시 동래구");
                convertedAreas.add("부산광역시 부산진구");
                convertedAreas.add("부산광역시 북구");
                convertedAreas.add("부산광역시 사상구");
                convertedAreas.add( "부산광역시 사하구");
                convertedAreas.add( "부산광역시 서구");
                convertedAreas.add( "부산광역시 수영구");
                convertedAreas.add( "부산광역시 연제구");
                convertedAreas.add( "부산광역시 영도구");
                convertedAreas.add( "부산광역시 중구");
                convertedAreas.add( "부산광역시 해운대구");
            }else if(area.equals("(울산)")){
                convertedAreas.add("울산광역시 중구");
                convertedAreas.add("울산광역시 남구");
                convertedAreas.add("울산광역시 동구");
                convertedAreas.add("울산광역시 북구");
                convertedAreas.add("울산광역시 울주군");
            }else if(area.equals("(세종)")){
                convertedAreas.add("세종특별자치시");
            }else if(area.equals("(경기)")){
                convertedAreas.add("경기도 가평군");
                convertedAreas.add("경기도 고양시");
                convertedAreas.add("경기도 과천시");
                convertedAreas.add("경기도 광명시");
                convertedAreas.add("경기도 광주시");
                convertedAreas.add("경기도 구리시");
                convertedAreas.add("경기도 군포시");
                convertedAreas.add("경기도 김포시");
                convertedAreas.add("경기도 남양주시");
                convertedAreas.add("경기도 동두천시");
                convertedAreas.add("경기도 부천시");
                convertedAreas.add("경기도 성남시");
                convertedAreas.add("경기도 수원시");
                convertedAreas.add("경기도 시흥시");
                convertedAreas.add("경기도 안산시");
                convertedAreas.add("경기도 안성시");
                convertedAreas.add("경기도 안양시");
                convertedAreas.add("경기도 양주시");
                convertedAreas.add("경기도 양평군");
                convertedAreas.add("경기도 여주시");
                convertedAreas.add("경기도 연천군");
                convertedAreas.add("경기도 오산시");
                convertedAreas.add("경기도 용인시");
                convertedAreas.add("경기도 의왕시");
                convertedAreas.add("경기도 의정부시");
                convertedAreas.add("경기도 이천시");
                convertedAreas.add("경기도 파주시");
                convertedAreas.add("경기도 평택시");
                convertedAreas.add("경기도 포천시");
                convertedAreas.add("경기도 하남시");
                convertedAreas.add("경기도 화성시");
            }else if(area.equals("(강원)")){
                convertedAreas.add("강원특별자치도 강릉시");
                convertedAreas.add("강원특별자치도 고성군");
                convertedAreas.add("강원특별자치도 동해시");
                convertedAreas.add("강원특별자치도 삼척시");
                convertedAreas.add("강원특별자치도 속초시");
                convertedAreas.add("강원특별자치도 양구군");
                convertedAreas.add("강원특별자치도 양양군");
                convertedAreas.add("강원특별자치도 영월군");
                convertedAreas.add("강원특별자치도 원주시");
                convertedAreas.add("강원특별자치도 인제군");
                convertedAreas.add("강원특별자치도 정선군");
                convertedAreas.add("강원특별자치도 철원군");
                convertedAreas.add("강원특별자치도 춘천시");
                convertedAreas.add("강원특별자치도 태백시");
                convertedAreas.add("강원특별자치도 평창군");
                convertedAreas.add("강원특별자치도 홍천군");
                convertedAreas.add("강원특별자치도 화천군");
                convertedAreas.add("강원특별자치도 횡성군");
            }else if(area.equals("(충북)")){
                convertedAreas.add("충청북도 괴산군");
                convertedAreas.add("충청북도 단양군");
                convertedAreas.add("충청북도 보은군");
                convertedAreas.add("충청북도 영동군");
                convertedAreas.add("충청북도 옥천군");
                convertedAreas.add("충청북도 음성군");
                convertedAreas.add("충청북도 제천시");
                convertedAreas.add("충청북도 진천군");
                convertedAreas.add("충청북도 청원군");
                convertedAreas.add("충청북도 청주시");
                convertedAreas.add("충청북도 충주시");
                convertedAreas.add("충청북도 증평군");
            }else if(area.equals("(충남)")){
                convertedAreas.add("충청남도 공주시");
                convertedAreas.add("충청남도 금산군");
                convertedAreas.add("충청남도 논산시");
                convertedAreas.add("충청남도 당진시");
                convertedAreas.add("충청남도 보령시");
                convertedAreas.add("충청남도 부여군");
                convertedAreas.add("충청남도 서산시");
                convertedAreas.add("충청남도 서천군");
                convertedAreas.add("충청남도 아산시");
                convertedAreas.add("충청남도 예산군");
                convertedAreas.add("충청남도 천안시");
                convertedAreas.add("충청남도 청양군");
                convertedAreas.add("충청남도 태안군");
                convertedAreas.add("충청남도 홍성군");
                convertedAreas.add("충청남도 계룡시");
            }else if(area.equals("(경북)")){
                convertedAreas.add("경상북도 경산시");
                convertedAreas.add("경상북도 경주시");
                convertedAreas.add("경상북도 고령군");
                convertedAreas.add("경상북도 구미시");
                convertedAreas.add("경상북도 김천시");
                convertedAreas.add("경상북도 문경시");
                convertedAreas.add("경상북도 봉화군");
                convertedAreas.add("경상북도 상주시");
                convertedAreas.add("경상북도 성주군");
                convertedAreas.add("경상북도 안동시");
                convertedAreas.add("경상북도 영덕군");
                convertedAreas.add("경상북도 영양군");
                convertedAreas.add("경상북도 영주시");
                convertedAreas.add("경상북도 영천시");
                convertedAreas.add("경상북도 예천군");
                convertedAreas.add("경상북도 울릉군");
                convertedAreas.add("경상북도 울진군");
                convertedAreas.add("경상북도 의성군");
                convertedAreas.add("경상북도 청도군");
                convertedAreas.add("경상북도 청송군");
                convertedAreas.add("경상북도 칠곡군");
                convertedAreas.add("경상북도 포항시");
            }else if(area.equals("(경남)")){
                convertedAreas.add("경상남도 거제시");
                convertedAreas.add("경상남도 거창시");
                convertedAreas.add("경상남도 고성군");
                convertedAreas.add("경상남도 김해시");
                convertedAreas.add("경상남도 남해군");
                convertedAreas.add("경상남도 마산시");
                convertedAreas.add("경상남도 밀양시");
                convertedAreas.add("경상남도 사천시");
                convertedAreas.add("경상남도 산청군");
                convertedAreas.add("경상남도 양산시");
                convertedAreas.add("경상남도 의령군");
                convertedAreas.add("경상남도 진주시");
                convertedAreas.add("경상남도 진해시");
                convertedAreas.add("경상남도 창녕군");
                convertedAreas.add("경상남도 창원시");
                convertedAreas.add("경상남도 통영시");
                convertedAreas.add("경상남도 하동군");
                convertedAreas.add("경상남도 함안군");
                convertedAreas.add("경상남도 함양군");
                convertedAreas.add("경상남도 합천군");
            }else if(area.equals("(전북)")){
                convertedAreas.add("전라북도 고창군");
                convertedAreas.add("전라북도 군산시");
                convertedAreas.add("전라북도 김제시");
                convertedAreas.add("전라북도 남원시");
                convertedAreas.add("전라북도 무주군");
                convertedAreas.add("전라북도 부안군");
                convertedAreas.add("전라북도 순창군");
                convertedAreas.add("전라북도 완주군");
                convertedAreas.add("전라북도 익산시");
                convertedAreas.add("전라북도 임실군");
                convertedAreas.add("전라북도 장수군");
                convertedAreas.add("전라북도 전주시");
                convertedAreas.add("전라북도 정읍시");
                convertedAreas.add("전라북도 진안군");
            }else if(area.equals("(전남)")){
                convertedAreas.add("전라남도 강진군");
                convertedAreas.add("전라남도 고흥군");
                convertedAreas.add("전라남도 곡성군");
                convertedAreas.add("전라남도 광양시");
                convertedAreas.add("전라남도 구례군");
                convertedAreas.add("전라남도 나주시");
                convertedAreas.add("전라남도 담양군");
                convertedAreas.add("전라남도 목포시");
                convertedAreas.add("전라남도 무안군");
                convertedAreas.add("전라남도 보성군");
                convertedAreas.add("전라남도 순천시");
                convertedAreas.add("전라남도 신안군");
                convertedAreas.add("전라남도 여수시");
                convertedAreas.add("전라남도 영광군");
                convertedAreas.add("전라남도 영암군");
                convertedAreas.add("전라남도 완도군");
                convertedAreas.add("전라남도 장성군");
                convertedAreas.add("전라남도 장흥군");
                convertedAreas.add("전라남도 진도군");
                convertedAreas.add("전라남도 함평군");
                convertedAreas.add("전라남도 해남군");
                convertedAreas.add("전라남도 화순군");
            }else if(area.equals("(제주)")){
                convertedAreas.add("제주도 남제주군");
                convertedAreas.add("제주도 북제주군");
                convertedAreas.add("제주도 서귀포시");
                convertedAreas.add("제주도 제주시");
            }

        }
        return convertedAreas;
    }


}
