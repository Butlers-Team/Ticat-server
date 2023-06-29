package Butlers.Ticat.weather.service;

import Butlers.Ticat.weather.entity.Region;
import Butlers.Ticat.weather.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {

    @Value("${resources.location}")
    private String resourceLocation;

    private final RegionRepository regionRepository;

    public void resetRegionList() {
        String fileLocation = resourceLocation + "/init/regionList.csv";
        Path path = Paths.get(fileLocation); // Path 클래스는 File 클래스와 비교하여 개선된 파일 및 디렉토리 경로를 나타내는 Java API. Path 클래스는 Java 7에서 소개되었으며, 파일 시스템 경로를 나타내는 데 보다 향상된 기능을 제공
        URI uri = path.toUri(); // toUri() 메서드는 Path 객체를 URI 객체로 변환할 때 file://을 접두사로 사용하는 경로를 생성

        try (BufferedReader br = new BufferedReader(new InputStreamReader( // InputStreamReader를 사용하여 UrlResource의 입력 스트림을 BufferedReader로 래핑. 이렇게 하면 효율적으로 파일을 읽을 수 있다.
                new UrlResource(uri).getInputStream()))) //UrlResource 클래스를 사용하여 URI 객체를 기반으로 UrlResource 인스턴스를 생성. UrlResource는 주어진 URL 또는 URI를 나타내는 Resource 인터페이스의 구현체 , getInputStream() 메소드를 호출하여 UrlResource의 입력 스트림을 얻는다. 이는 BufferedReader에 전달하여 파일을 읽을 수 있도록 한다.
        {
            String line = br.readLine(); // BufferedReader를 사용하여 첫 번째 줄을 읽고 line 변수에 할당. 파일의 헤더를 읽어 온다.
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                regionRepository.save(new Region(Long.parseLong(splits[0]), splits[1], splits[2],
                        Integer.parseInt(splits[3]), Integer.parseInt(splits[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getRegionId(int currentLatitude,int currentLongitude){
        List<Region> allRegions = regionRepository.findAll();

        // 가장 가까운 지역을 저장할 변수와 초기 거리를 설정
        Region closestRegion = null;
        int minDistance = Integer.MAX_VALUE;

        // 모든 지역을 순회하면서 가장 가까운 지역을 찾음
        for (Region region : allRegions) {
            int regionLatitude = region.getNx();
            int regionLongitude = region.getNy();

            // 두 지점 간의 거리 계산
            double distance = calculateDistance(currentLatitude, currentLongitude, regionLatitude, regionLongitude);

            // 현재까지의 최소 거리보다 작은 거리가 나타나면 가장 가까운 지역을 업데이트
            if (distance < minDistance) {
                minDistance = (int) distance;
                closestRegion = region;
            }
        }

        if (closestRegion != null) {
            return closestRegion.getId();
        } else {
            return null;
        }

    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0; // 지구 반지름 (단위: km)

        // 위도 및 경도를 라디안 단위로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 위도 차이와 경도 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine 공식을 사용하여 거리 계산
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance;
    }
}
