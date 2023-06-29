package Butlers.Ticat.weather.service;

import Butlers.Ticat.weather.dto.WeatherDto;
import Butlers.Ticat.weather.entity.Region;
import Butlers.Ticat.weather.entity.Weather;
import Butlers.Ticat.weather.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WeatherApiService {

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;
    private final RegionRepository regionRepository;

    public WeatherDto.Response getRegionWeather(Long regionId){
        // 1. 날씨 정보를 요청한 지역 조회
        Optional<Region> optionalRegion = regionRepository.findById(regionId);
        Region region = optionalRegion.orElseThrow();  // 예외 처리 로직 추가 해야함

        StringBuilder urlBuilder =  new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst");

        // 2. 요청 시각 조회
        LocalDateTime now = LocalDateTime.now();
        String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hour = now.getHour();
        int min = now.getMinute();
        if(min <= 30) { // 해당 시각 발표 전에는 자료가 없음 - 이전시각을 기준으로 해야함
            hour -= 1;
        }
        String hourStr = hour + "00"; // 정시 기준
        String nx = Integer.toString(region.getNx());
        String ny = Integer.toString(region.getNy());
        String currentChangeTime = now.format(DateTimeFormatter.ofPattern("yy.MM.dd ")) + hour;

        // 기준 시각 조회 자료가 이미 존재하고 있다면 API 요청 없이 기존 자료 그대로 넘김
        Weather prevWeather = region.getWeather();
        if(prevWeather != null && prevWeather.getLastUpdateTime() != null) {
            if(prevWeather.getLastUpdateTime().equals(currentChangeTime)) {
                log.info("기존 자료를 재사용합니다");
                return WeatherDto.Response.builder()
                        .weather(prevWeather)
                        .message("날씨를 불러 왔습니다.").build();
            }
        }

        log.info("API 요청 발송 >>> 지역: {}, 연월일: {}, 시각: {}", region, yyyyMMdd, hourStr);

        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(yyyyMMdd, "UTF-8")); /*‘21년 6월 28일 발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(hourStr, "UTF-8")); /*06시 발표(정시단위) */
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

            URL url = new URL(urlBuilder.toString());
            log.info("request url: {}", url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

            System.out.println(data);
            //// 응답 수신 완료 ////
            //// 응답 결과를 JSON 파싱 ////

            Double temp = null;
            Double humid = null;
            String sky = "";
            String rainAmount = "";

            JSONObject jObject = new JSONObject(data);
            JSONObject response = jObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray jArray = items.getJSONArray("item");

            for(int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String category = obj.getString("category");
                Object fcstValue = obj.get("fcstValue");

                switch (category) {
                    case "T1H":
                        temp = Double.parseDouble((String) fcstValue);
                        break;
                    case "RN1":
                        rainAmount = (String)fcstValue;
                        break;
                    case "SKY":
                        sky = (String) fcstValue;

                        if(sky.equals("1")) sky = "맑음";
                        else if(sky.equals("3")) sky = "구름많음";
                        else sky = "흐림";

                        break;
                    case "REH":
                        humid = Double.parseDouble((String) fcstValue);
                        break;
                }
            }

            Weather weather = new Weather(temp, rainAmount, humid, currentChangeTime,sky);
            region.updateRegionWeather(weather); // DB 업데이트
            return WeatherDto.Response.builder()
                    .region(region.getParentRegion() +" "+ region.getChildRegion())
                    .weather(weather)
                    .message("날씨를 불러 왔습니다.").build();

        } catch (IOException e) {
            return WeatherDto.Response.builder()
                    .region(null)
                    .weather(null)
                    .message("날씨 정보를 불러오는 중 오류가 발생했습니다").build();
        }
    }
}
