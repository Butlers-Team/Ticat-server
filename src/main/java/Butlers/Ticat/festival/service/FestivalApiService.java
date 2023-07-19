package Butlers.Ticat.festival.service;

import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import static Butlers.Ticat.festival.helper.AreaNameMapping.areaMap;
import static Butlers.Ticat.festival.helper.AreaNameMapping.determineArea;
import static Butlers.Ticat.festival.helper.KeywordCategoryMapping.determineCategory;
import static Butlers.Ticat.festival.helper.KeywordCategoryMapping.keywordCategoryMap;

@Service
@RequiredArgsConstructor
public class FestivalApiService {

    @Value("${tourApi.serviceKey}")
    private String serviceKey;

    private final FestivalRepository festivalRepository;

    @Scheduled(cron = "0 0 0 * * *") // 자정 설정
    public void getFestivalList() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/areaBasedList1");
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("D", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("15", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String data = sb.toString();

        JSONObject jObject = new JSONObject(data);
        JSONObject response = jObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray jArray = items.getJSONArray("item");

        for(int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);

            String title = obj.get("title").toString();
            String address = obj.get("addr1").toString();
            String contentId = obj.get("contentid").toString();
            String areacode = obj.get("areacode").toString();
            String sigungucode = obj.get("sigungucode").toString();

            String area = determineArea(areacode+sigungucode,areaMap);

            String image = obj.get("firstimage").toString();
            double mapx = Double.parseDouble(obj.get("mapx").toString());
            double mapy = Double.parseDouble(obj.get("mapy").toString());
            String tel = obj.get("tel").toString();
            Optional<Festival> optionalFestival = festivalRepository.findById(Long.parseLong(contentId));


            if (optionalFestival.isEmpty()) { // contentId가 중복되지 않은 경우에만 저장 진행
                festivalRepository.save(new Festival(Long.parseLong(contentId),title,address,area,image,mapx,mapy,tel));
            }
        }
    }

    public void getFestivalDetail() throws IOException {

        List<Festival> festivalList = festivalRepository.findAll(Sort.by(Sort.Direction.DESC, "festivalId"));


        for(Festival festival : festivalList){

            if(festival.getDetailFestival().getEventstartdate() != null) continue;

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailIntro1");
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(festival.getFestivalId().toString(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("15", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data = sb.toString();

            try {
                JSONObject jObject = new JSONObject(data);
                JSONObject response = jObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                JSONArray jArray = items.getJSONArray("item");


                JSONObject obj = jArray.getJSONObject(0);

                String sponsor1 = obj.get("sponsor1").toString();
                String sponsor1tel = obj.get("sponsor1tel").toString();
                String sponsor2 = obj.get("sponsor2").toString();
                String eventstartdate = obj.get("eventstartdate").toString();
                String eventenddate = obj.get("eventenddate").toString();
                String playtime = obj.get("playtime").toString();
                String eventplace = obj.get("eventplace").toString();
                String usetimefestival = obj.get("usetimefestival").toString();
                String eventhomepage = obj.get("eventhomepage").toString();

                festival.updateDetailFestival(new DetailFestival(sponsor1, sponsor1tel, sponsor2, eventstartdate, eventenddate, eventhomepage, playtime, eventplace, usetimefestival));

                festivalRepository.save(festival);
            }catch (JSONException e) {
                System.err.println("Failed to parse JSON data: " + e.getMessage());
                continue;
            }
        }
    }

    public void getFestivalOverView() throws IOException {

        List<Festival> festivalList = festivalRepository.findAll(Sort.by(Sort.Direction.DESC, "festivalId"));


        for(Festival festival : festivalList){

            if(festival.getDetailFestival().getOverview() != null) continue;

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailCommon1");
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(festival.getFestivalId().toString(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("15", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data = sb.toString();

            try {
                JSONObject jObject = new JSONObject(data);
                JSONObject response = jObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                JSONArray jArray = items.getJSONArray("item");


                JSONObject obj = jArray.getJSONObject(0);
                String overview = obj.optString("overview", ""); // null일 경우 ""로 반환됨
                String category = determineCategory(overview, keywordCategoryMap);

                if(festival.getDetailFestival().getEventstartdate() != null &&  festival.getDetailFestival().getEventenddate() != null){
                    festival.getDetailFestival().updateStatus();
                }

                festival.getDetailFestival().updateCategoryAndOverView(category, overview);

                festivalRepository.save(festival);

            }catch (Exception e) {
                System.err.println("Failed to parse JSON data: " + e.getMessage());
                continue;
            }
        }
    }
    @Scheduled(cron = "0 1 0 * * *") // 매일 0시 1분 실행
    public void checkStatus(){
        List<Festival> festivalList = festivalRepository.findAll();

        for(Festival festival : festivalList){
            if(festival.getDetailFestival().getEventstartdate() != null &&  festival.getDetailFestival().getEventenddate() != null){
                festival.getDetailFestival().updateStatus();
                festivalRepository.save(festival);
            }
        }
    }

    // 스케줄러를 사용해 지정 시간이 되면 데이터를 불러오도록 설정
    @Scheduled(cron = "15 0 0 * * *") // 매일 0시 0분 15초 실행
    public void insertDataDetailScheduler() throws IOException {
        List<Festival> festivalList = festivalRepository.findAll(Sort.by(Sort.Direction.DESC, "festivalId"));

        for (int i = 0; i < Math.min(festivalList.size(), 10); i++) {
            Festival festival = festivalList.get(i);

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailIntro1");
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(festival.getFestivalId().toString(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("15", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data = sb.toString();

            try {
                JSONObject jObject = new JSONObject(data);
                JSONObject response = jObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                JSONArray jArray = items.getJSONArray("item");


                JSONObject obj = jArray.getJSONObject(0);

                String sponsor1 = obj.get("sponsor1").toString();
                String sponsor1tel = obj.get("sponsor1tel").toString();
                String sponsor2 = obj.get("sponsor2").toString();
                String eventstartdate = obj.get("eventstartdate").toString();
                String eventenddate = obj.get("eventenddate").toString();
                String playtime = obj.get("playtime").toString();
                String eventplace = obj.get("eventplace").toString();
                String usetimefestival = obj.get("usetimefestival").toString();
                String eventhomepage = obj.get("eventhomepage").toString();

                festival.updateDetailFestival(new DetailFestival(sponsor1, sponsor1tel, sponsor2, eventstartdate, eventenddate, eventhomepage, playtime, eventplace, usetimefestival));

                festivalRepository.save(festival);
            }catch (JSONException e) {
                System.err.println("Failed to parse JSON data: " + e.getMessage());
                continue;
            }
        }
    }

    // 스케줄러를 사용해 지정 시간이 되면 데이터를 불러오도록 설정
    @Scheduled(cron = "30 0 0 * * *") // 매일 0시 0분 30초 실행
    public void insertDataOverViewScheduler() throws IOException {
        List<Festival> festivalList = festivalRepository.findAll(Sort.by(Sort.Direction.DESC, "festivalId"));


        for(Festival festival : festivalList){

            if(festival.getDetailFestival().getOverview() != null) continue;

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailCommon1");
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(festival.getFestivalId().toString(), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("15", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data = sb.toString();

            try {
                JSONObject jObject = new JSONObject(data);
                JSONObject response = jObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                JSONArray jArray = items.getJSONArray("item");


                JSONObject obj = jArray.getJSONObject(0);
                String overview = obj.optString("overview", ""); // null일 경우 ""로 반환됨
                String category = determineCategory(overview, keywordCategoryMap);

                if(festival.getDetailFestival().getEventstartdate() != null &&  festival.getDetailFestival().getEventenddate() != null){
                    festival.getDetailFestival().updateStatus();
                }

                festival.getDetailFestival().updateCategoryAndOverView(category, overview);

                festivalRepository.save(festival);

            }catch (Exception e) {
                System.err.println("Failed to parse JSON data: " + e.getMessage());
                continue;
            }
        }
    }
}
