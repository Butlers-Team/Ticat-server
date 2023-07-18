package Butlers.Ticat.festival.mapper;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-19T00:25:53+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class FestivalMapperImpl implements FestivalMapper {

    @Override
    public FestivalDto.Response festivalToResponse(Festival festival) {
        if ( festival == null ) {
            return null;
        }

        FestivalDto.Response response = new FestivalDto.Response();

        response.setCategory( festivalDetailFestivalCategory( festival ) );
        response.setEventstartdate( festivalDetailFestivalEventstartdate( festival ) );
        response.setEventenddate( festivalDetailFestivalEventenddate( festival ) );
        response.setOverview( festivalDetailFestivalOverview( festival ) );
        response.setEventhomepage( festivalDetailFestivalEventhomepage( festival ) );
        response.setEventplace( festivalDetailFestivalEventplace( festival ) );
        response.setPrice( festivalDetailFestivalUsetimefestival( festival ) );
        response.setPlaytime( festivalDetailFestivalPlaytime( festival ) );
        DetailFestival.Status status = festivalDetailFestivalStatus( festival );
        if ( status != null ) {
            response.setStatus( status.name() );
        }
        response.setFestivalId( festival.getFestivalId() );
        response.setImage( festival.getImage() );
        response.setTitle( festival.getTitle() );
        response.setAddress( festival.getAddress() );
        response.setTel( festival.getTel() );
        response.setMapx( festival.getMapx() );
        response.setMapy( festival.getMapy() );

        return response;
    }

    @Override
    public FestivalDto.ListResponse festivalToListResponse(Festival festival) {
        if ( festival == null ) {
            return null;
        }

        FestivalDto.ListResponse listResponse = new FestivalDto.ListResponse();

        listResponse.setEventstartdate( festivalDetailFestivalEventstartdate( festival ) );
        listResponse.setEventenddate( festivalDetailFestivalEventenddate( festival ) );
        listResponse.setCategory( festivalDetailFestivalCategory( festival ) );
        DetailFestival.Status status = festivalDetailFestivalStatus( festival );
        if ( status != null ) {
            listResponse.setStatus( status.name() );
        }
        listResponse.setFestivalId( festival.getFestivalId() );
        listResponse.setTitle( festival.getTitle() );
        listResponse.setImage( festival.getImage() );
        listResponse.setAddress( festival.getAddress() );
        listResponse.setReviewRating( festival.getReviewRating() );
        listResponse.setReviewCount( festival.getReviewCount() );
        listResponse.setLikeCount( festival.getLikeCount() );
        listResponse.setArea( festival.getArea() );
        listResponse.setMapx( festival.getMapx() );
        listResponse.setMapy( festival.getMapy() );

        return listResponse;
    }

    @Override
    public List<FestivalDto.ListResponse> festivalsToFestivalListResponses(List<Festival> festivals) {
        if ( festivals == null ) {
            return null;
        }

        List<FestivalDto.ListResponse> list = new ArrayList<FestivalDto.ListResponse>( festivals.size() );
        for ( Festival festival : festivals ) {
            list.add( festivalToListResponse( festival ) );
        }

        return list;
    }

    private String festivalDetailFestivalCategory(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String category = detailFestival.getCategory();
        if ( category == null ) {
            return null;
        }
        return category;
    }

    private String festivalDetailFestivalEventstartdate(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String eventstartdate = detailFestival.getEventstartdate();
        if ( eventstartdate == null ) {
            return null;
        }
        return eventstartdate;
    }

    private String festivalDetailFestivalEventenddate(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String eventenddate = detailFestival.getEventenddate();
        if ( eventenddate == null ) {
            return null;
        }
        return eventenddate;
    }

    private String festivalDetailFestivalOverview(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String overview = detailFestival.getOverview();
        if ( overview == null ) {
            return null;
        }
        return overview;
    }

    private String festivalDetailFestivalEventhomepage(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String eventhomepage = detailFestival.getEventhomepage();
        if ( eventhomepage == null ) {
            return null;
        }
        return eventhomepage;
    }

    private String festivalDetailFestivalEventplace(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String eventplace = detailFestival.getEventplace();
        if ( eventplace == null ) {
            return null;
        }
        return eventplace;
    }

    private String festivalDetailFestivalUsetimefestival(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String usetimefestival = detailFestival.getUsetimefestival();
        if ( usetimefestival == null ) {
            return null;
        }
        return usetimefestival;
    }

    private String festivalDetailFestivalPlaytime(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        String playtime = detailFestival.getPlaytime();
        if ( playtime == null ) {
            return null;
        }
        return playtime;
    }

    private DetailFestival.Status festivalDetailFestivalStatus(Festival festival) {
        if ( festival == null ) {
            return null;
        }
        DetailFestival detailFestival = festival.getDetailFestival();
        if ( detailFestival == null ) {
            return null;
        }
        DetailFestival.Status status = detailFestival.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }
}
