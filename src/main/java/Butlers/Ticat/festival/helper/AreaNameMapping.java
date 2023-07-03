package Butlers.Ticat.festival.helper;

import java.util.HashMap;
import java.util.Map;

public class AreaNameMapping {
    public static final Map<String, String> areaMap = new HashMap<>();

    static {
        // 서울특별시
        areaMap.put("11", "(서울)강남구");
        areaMap.put("12", "(서울)강동구");
        areaMap.put("13", "(서울)강북구");
        areaMap.put("14", "(서울)강서구");
        areaMap.put("15", "(서울)관악구");
        areaMap.put("16", "(서울)광진구");
        areaMap.put("17", "(서울)구로구");
        areaMap.put("18", "(서울)금천구");
        areaMap.put("19", "(서울)노원구");
        areaMap.put("110", "(서울)도봉구");
        areaMap.put("111", "(서울)동대문구");
        areaMap.put("112", "(서울)동작구");
        areaMap.put("113", "(서울)마포구");
        areaMap.put("114", "(서울)서대문구");
        areaMap.put("115", "(서울)서초구");
        areaMap.put("116", "(서울)성동구");
        areaMap.put("117", "(서울)성북구");
        areaMap.put("118", "(서울)송파구");
        areaMap.put("119", "(서울)양천구");
        areaMap.put("120", "(서울)영등포구");
        areaMap.put("121", "(서울)용산구");
        areaMap.put("122", "(서울)은평구");
        areaMap.put("123", "(서울)종로구");
        areaMap.put("124", "(서울)중구");
        areaMap.put("125", "(서울)중랑구");

        // 인천
        areaMap.put("21", "(인천)강화군");
        areaMap.put("22", "(인천)계양구");
        areaMap.put("23", "(인천)미추홀구");
        areaMap.put("24", "(인천)남동구");
        areaMap.put("25", "(인천)동구");
        areaMap.put("26", "(인천)부평구");
        areaMap.put("27", "(인천)서구");
        areaMap.put("28", "(인천)연수구");
        areaMap.put("29", "(인천)웅진군");
        areaMap.put("210", "(인천)중구");


        // 대전
        areaMap.put("31", "(대전)대덕구");
        areaMap.put("32", "(대전)동구");
        areaMap.put("33", "(대전)서구");
        areaMap.put("34", "(대전)유성구");
        areaMap.put("35", "(대전)중구");

        // 대구
        areaMap.put("41", "(대구)남구");
        areaMap.put("42", "(대구)달서구");
        areaMap.put("43", "(대구)달성군");
        areaMap.put("44", "(대구)동구");
        areaMap.put("45", "(대구)북구");
        areaMap.put("46", "(대구)서구");
        areaMap.put("47", "(대구)수성구");
        areaMap.put("48", "(대구)중구");

        // 광주
        areaMap.put("51", "(광주)광산구");
        areaMap.put("52", "(광주)남구");
        areaMap.put("53", "(광주)동구");
        areaMap.put("54", "(광주)북구");
        areaMap.put("55", "(광주)서구");

        // 부산
        areaMap.put("61", "(부산)강서구");
        areaMap.put("62", "(부산)금정구");
        areaMap.put("63", "(부산)기장군");
        areaMap.put("64", "(부산)남구");
        areaMap.put("65", "(부산)동구");
        areaMap.put("66", "(부산)동래구");
        areaMap.put("67", "(부산)부산진구");
        areaMap.put("68", "(부산)북구");
        areaMap.put("69", "(부산)사상구");
        areaMap.put("610", "(부산)사하구");
        areaMap.put("611", "(부산)서구");
        areaMap.put("612", "(부산)수영구");
        areaMap.put("613", "(부산)연제구");
        areaMap.put("614", "(부산)영도구");
        areaMap.put("615", "(부산)중구");
        areaMap.put("616", "(부산)해운대구");

        // 울산
        areaMap.put("71", "(울산)중구");
        areaMap.put("72", "(울산)남구");
        areaMap.put("73", "(울산)동군");
        areaMap.put("74", "(울산)북구");
        areaMap.put("75", "(울산)울주구");

        // 세종
        areaMap.put("81", "(세종)세종특별자치시");

        // 경기도
        areaMap.put("311", "(경기)가평군");
        areaMap.put("312", "(경기)고양시");
        areaMap.put("313", "(경기)과천시");
        areaMap.put("314", "(경기)광명시");
        areaMap.put("315", "(경기)광주시");
        areaMap.put("316", "(경기)구리시");
        areaMap.put("317", "(경기)군포시");
        areaMap.put("318", "(경기)김포시");
        areaMap.put("319", "(경기)남양주시");
        areaMap.put("3110", "(경기)동두천시");
        areaMap.put("3111", "(경기)부천시");
        areaMap.put("3112", "(경기)성남시");
        areaMap.put("3113", "(경기)수원시");
        areaMap.put("3114", "(경기)시흥시");
        areaMap.put("3115", "(경기)안산시");
        areaMap.put("3116", "(경기)안성시");
        areaMap.put("3117", "(경기)안양시");
        areaMap.put("3118", "(경기)양주시");
        areaMap.put("3119", "(경기)양평군");
        areaMap.put("3120", "(경기)여주시");
        areaMap.put("3121", "(경기)연천군");
        areaMap.put("3122", "(경기)오산시");
        areaMap.put("3123", "(경기)용인시");
        areaMap.put("3124", "(경기)의왕시");
        areaMap.put("3125", "(경기)의정부시");
        areaMap.put("3126", "(경기)이천시");
        areaMap.put("3127", "(경기)파주시");
        areaMap.put("3128", "(경기)평택시");
        areaMap.put("3129", "(경기)포천시");
        areaMap.put("3130", "(경기)하남시");
        areaMap.put("3131", "(경기)화성시");

        // 강원도
        areaMap.put("321", "(강원)강릉시");
        areaMap.put("322", "(강원)고성군");
        areaMap.put("323", "(강원)동해시");
        areaMap.put("324", "(강원)삼척시");
        areaMap.put("325", "(강원)속초시");
        areaMap.put("326", "(강원)양구군");
        areaMap.put("327", "(강원)양양군");
        areaMap.put("328", "(강원)영월군");
        areaMap.put("329", "(강원)원주시");
        areaMap.put("3210", "(강원)인제군");
        areaMap.put("3211", "(강원)정선군");
        areaMap.put("3212", "(강원)철원군");
        areaMap.put("3213", "(강원)춘천시");
        areaMap.put("3214", "(강원)태백시");
        areaMap.put("3215", "(강원)평창군");
        areaMap.put("3216", "(강원)홍천군");
        areaMap.put("3217", "(강원)화천군");
        areaMap.put("3218", "(강원)횡성군");

        // 충청북도
        areaMap.put("331", "(충북)괴산군");
        areaMap.put("332", "(충북)단양군");
        areaMap.put("333", "(충북)보은군");
        areaMap.put("334", "(충북)영동군");
        areaMap.put("335", "(충북)옥천군");
        areaMap.put("336", "(충북)음성군");
        areaMap.put("337", "(충북)제천시");
        areaMap.put("338", "(충북)진천군");
        areaMap.put("339", "(충북)청원군");
        areaMap.put("3310", "(충북)청주시");
        areaMap.put("3311", "(충북)충주시");
        areaMap.put("3312", "(충북)증평군");


        // 충청남도
        areaMap.put("341", "(충남)공주시");
        areaMap.put("342", "(충남)금산군");
        areaMap.put("343", "(충남)논산시");
        areaMap.put("344", "(충남)당진시");
        areaMap.put("345", "(충남)보령시");
        areaMap.put("346", "(충남)부여군");
        areaMap.put("347", "(충남)서산시");
        areaMap.put("348", "(충남)서천군");
        areaMap.put("349", "(충남)아산시");
        areaMap.put("3411", "(충남)예산군");
        areaMap.put("3412", "(충남)천안시");
        areaMap.put("3413", "(충남)청양군");
        areaMap.put("3414", "(충남)태안군");
        areaMap.put("3415", "(충남)홍성군");
        areaMap.put("3416", "(충남)계룡시");

        // 경상북도
        areaMap.put("351", "(경북)경산시");
        areaMap.put("352", "(경북)경주시");
        areaMap.put("353", "(경북)고령군");
        areaMap.put("354", "(경북)구미시");
        areaMap.put("356", "(경북)김천시");
        areaMap.put("357", "(경북)문경시");
        areaMap.put("358", "(경북)봉화군");
        areaMap.put("359", "(경북)상주시");
        areaMap.put("3510", "(경북)성주군");
        areaMap.put("3511", "(경북)안동시");
        areaMap.put("3512", "(경북)영덕군");
        areaMap.put("3513", "(경북)영양군");
        areaMap.put("3514", "(경북)영주시");
        areaMap.put("3515", "(경북)영천시");
        areaMap.put("3516", "(경북)예천군");
        areaMap.put("3517", "(경북)울릉군");
        areaMap.put("3518", "(경북)울진군");
        areaMap.put("3519", "(경북)의성군");
        areaMap.put("3520", "(경북)청도군");
        areaMap.put("3521", "(경북)청송군");
        areaMap.put("3522", "(경북)칠곡군");
        areaMap.put("3523", "(경북)포항시");

        // 경상남도
        areaMap.put("361", "(경남)거제시");
        areaMap.put("362", "(경남)거창시");
        areaMap.put("363", "(경남)고성군");
        areaMap.put("364", "(경남)김해시");
        areaMap.put("365", "(경남)남해군");
        areaMap.put("366", "(경남)마산시");
        areaMap.put("367", "(경남)밀양시");
        areaMap.put("368", "(경남)사천시");
        areaMap.put("369", "(경남)산청군");
        areaMap.put("3610", "(경남)양산시");
        areaMap.put("3612", "(경남)의령군");
        areaMap.put("3613", "(경남)진주시");
        areaMap.put("3614", "(경남)진해시");
        areaMap.put("3615", "(경남)창녕군");
        areaMap.put("3616", "(경남)창원시");
        areaMap.put("3617", "(경남)통영시");
        areaMap.put("3618", "(경남)하동군");
        areaMap.put("3619", "(경남)함안군");
        areaMap.put("3620", "(경남)함양군");
        areaMap.put("3621", "(경남)합천군");

        // 전라북도
        areaMap.put("371", "(전북)고창군");
        areaMap.put("372", "(전북)군산시");
        areaMap.put("373", "(전북)김제시");
        areaMap.put("374", "(전북)남원시");
        areaMap.put("375", "(전북)무주군");
        areaMap.put("376", "(전북)부안군");
        areaMap.put("377", "(전북)순창군");
        areaMap.put("378", "(전북)완주군");
        areaMap.put("379", "(전북)익산시");
        areaMap.put("3710", "(전북)임실군");
        areaMap.put("3711", "(전북)장수군");
        areaMap.put("3712", "(전북)전주시");
        areaMap.put("3713", "(전북)정읍시");
        areaMap.put("3714", "(전북)진안군");

        // 전라남도
        areaMap.put("381", "(전남)강진군");
        areaMap.put("382", "(전남)고흥군");
        areaMap.put("383", "(전남)곡성군");
        areaMap.put("384", "(전남)광양시");
        areaMap.put("385", "(전남)구례군");
        areaMap.put("386", "(전남)나주시");
        areaMap.put("387", "(전남)담양군");
        areaMap.put("388", "(전남)목포시");
        areaMap.put("389", "(전남)무안군");
        areaMap.put("3810", "(전남)보성군");
        areaMap.put("3811", "(전남)순천시");
        areaMap.put("3812", "(전남)신안군");
        areaMap.put("3813", "(전남)여수시");
        areaMap.put("3816", "(전남)영광군");
        areaMap.put("3817", "(전남)영암군");
        areaMap.put("3818", "(전남)완도군");
        areaMap.put("3819", "(전남)장성군");
        areaMap.put("3820", "(전남)장흥군");
        areaMap.put("3821", "(전남)진도군");
        areaMap.put("3822", "(전남)함평군");
        areaMap.put("3823", "(전남)해남군");
        areaMap.put("3824", "(전남)화순군");

        // 제주도
        areaMap.put("391", "(제주)남제주군");
        areaMap.put("392", "(제주)북제주군");
        areaMap.put("393", "(제주)서귀포시");
        areaMap.put("394", "(제주)제주시");

    }

    public static String determineArea(String areaCode, Map<String, String> areaMap) {
        for (Map.Entry<String, String> entry : areaMap.entrySet()) {
            String keyword = entry.getKey();
            String area = entry.getValue();
            if (areaCode.equals(keyword)) {
                return area;
            }
        }
        return "기타";
    }
}
