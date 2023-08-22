package Butlers.Ticat.festival.contoller;

import Butlers.Ticat.festival.controller.FestivalController;
import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.mapper.FestivalMapper;
import Butlers.Ticat.festival.service.FestivalService;
import Butlers.Ticat.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FestivalController.class)
public class FestivalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FestivalService festivalService;
    @MockBean
    private FestivalMapper mapper;
    @MockBean
    private MemberRepository memberRepository;
    @Test
    void 축제_상세_페이지() throws Exception {
        Long festivalId = 1L;

        Festival Festival = new Festival();
        FestivalDto.Response ResponseFestival = new FestivalDto.Response();

        Mockito.when(festivalService.findFestival(anyLong())).thenReturn(Festival);
        Mockito.when(festivalService.isFestivalLiked(any(FestivalDto.Response.class))).thenReturn(ResponseFestival);

        mockMvc.perform(get("/festivals/{festival-id}", festivalId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 거리_안_축제_찾기() throws Exception {
        double mapX = 127.54321;
        double mapY = 37.12345;
        double distance = 10.0;

        List<Festival> festivals = new ArrayList<>();
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();

        Mockito.when(festivalService.findFestivalsWithinDistance(anyDouble(),anyDouble(),anyDouble())).thenReturn(festivals);
        Mockito.when(mapper.festivalsToFestivalListResponses(any())).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/distance")
                        .param("mapX", String.valueOf(mapX))
                        .param("mapY", String.valueOf(mapY))
                        .param("distance", String.valueOf(distance)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 메인페이지_배너() throws Exception {
        List<Festival> festivals = new ArrayList<>();
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();

        Mockito.when(festivalService.findFestivalByStatus()).thenReturn(festivals);
        Mockito.when(mapper.festivalsToFestivalListResponses(any())).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/banner"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 축제_리스트_페이지() throws Exception {
        String category = "정원";
        List<String> areas = Arrays.asList("서울");
        int page = 1;
        int size = 10;

        List<Festival> festivals = new ArrayList<>();
        Page<Festival> pageFestivals = new PageImpl<>(festivals);
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();

        Mockito.when(festivalService.getFilteredFestivalList(anyString(),anyList(),anyInt(),anyInt())).thenReturn(pageFestivals);
        Mockito.when(mapper.festivalsToFestivalListResponses(any())).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/list")
                        .param("category",category)
                        .param("areas", areas.toArray(new String[0]))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 상세페이지_축제_추천() throws Exception {
        List<Festival> festivals = new ArrayList<>();
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();
        String category = "음악";

        Mockito.when(festivalService.findDetailRecommend(anyString())).thenReturn(festivals);
        Mockito.when(mapper.festivalsToFestivalListResponses(any())).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/detailrecommend")
                        .param("category",category))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 메인페이지_관심사_축제_추천() throws Exception {
        List<Festival> festivals = new ArrayList<>();
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();

        Mockito.when(festivalService.findMainRecommend()).thenReturn(festivals);
        Mockito.when(mapper.festivalsToFestivalListResponses(any())).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/mainrecommend"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 두_좌표_사이_거리_구하기() throws Exception {
        double lat1 = 127.54321;
        double lat2 = 124.54321;
        double lon1 = 37.5445;
        double lon2 = 36.4345;
        FestivalDto.DistanceResponse distance = new FestivalDto.DistanceResponse();

        Mockito.when(festivalService.calculateDistance(anyDouble(),anyDouble(),anyDouble(),anyDouble())).thenReturn(distance);

        mockMvc.perform(get("/festivals/km")
                        .param("lat1",String.valueOf(lat1))
                        .param("lat2",String.valueOf(lat2))
                        .param("lon1",String.valueOf(lon1))
                        .param("lon2",String.valueOf(lon2)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void 축제_제목_검색() throws Exception{
        String title = "버스킹";
        int page = 1;
        int size = 10;

        List<Festival> festivals = new ArrayList<>();
        Page<Festival> festivalPage = new PageImpl<>(festivals);
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();

        Mockito.when(festivalService.findFestivalsByTitle(anyString(),anyInt(),anyInt())).thenReturn(festivalPage);
        Mockito.when(mapper.festivalsToFestivalListResponses(any())).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/calendar")
                        .param("title",title)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 지도_페이지() throws Exception {
        double longitude = 126.9816417;
        double latitude = 37.57037778;
        List<String> categories = Arrays.asList("category1", "category2");
        String sortBy = "name";
        List<DetailFestival.Status> status = Arrays.asList(DetailFestival.Status.ONGOING);
        String keyword = "keyword";
        int page = 1;
        int size = 10;

        List<Festival> festivals = new ArrayList<>();
        Page<Festival> pageFestivals = new PageImpl<>(festivals);
        List<FestivalDto.ListResponse> responseFestivals = new ArrayList<>();

        if (keyword != null) {
            Mockito.when(festivalService.findByKeywordAndAreas(anyString(), anyList(), anyString(), anyList(), anyInt(), anyInt()))
                    .thenReturn(pageFestivals);
        } else {
            Mockito.when(festivalService.getFilteredFestivals(anyDouble(), anyDouble(), anyList(), anyString(), anyList(), anyInt(), anyInt()))
                    .thenReturn(pageFestivals);
        }

        Mockito.when(mapper.festivalsToFestivalListResponses(festivals)).thenReturn(responseFestivals);

        mockMvc.perform(get("/festivals/map")
                        .param("longitude", String.valueOf(longitude))
                        .param("latitude", String.valueOf(latitude))
                        .param("categories", String.join(",", categories))
                        .param("sortBy", sortBy)
                        .param("status", DetailFestival.Status.ONGOING.toString())
                        .param("keyword", keyword)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andReturn();
    }
}
