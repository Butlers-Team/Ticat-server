package Butlers.Ticat.blog.service;

import Butlers.Ticat.blog.dto.NaverBlogDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NaverBlogSearchApiService {
    @Value("${naver.clientId}")
    String clientId;
    @Value("${naver.clientSecret}")
    String clientSecret;

    public List<NaverBlogDto.Response> search(String query) {

        List<NaverBlogDto.Response> blogPosts = new ArrayList<>();

        try { // 검색어 인코딩
            query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        // URL 세팅
        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + query;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray items = responseJson.getJSONArray("items");

        // JSON 객체에서 필요한 데이터 추출 및 Dto 객체에 매핑
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String title = item.getString("title");
            String link = item.getString("link");
            String description = item.getString("description");
            String bloggername = item.getString("bloggername");
            String postdate = item.getString("postdate");

            NaverBlogDto.Response blogPost = new NaverBlogDto.Response(title, link, description, bloggername, postdate);
            blogPosts.add(blogPost);
        }


//        System.out.println(responseBody);

        return blogPosts;
    }

    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){ // HTTP 연결
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){ // 응답 결과 가공
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
