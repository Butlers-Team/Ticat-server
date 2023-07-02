package Butlers.Ticat.festival.mapper;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.Festival;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FestivalMapper {

    @Mapping(source = "detailFestival.eventDate", target = "eventDate")
    @Mapping(source = "detailFestival.overview", target = "overview")
    @Mapping(source = "detailFestival.eventhomepage", target = "eventhomepage")
    @Mapping(source = "detailFestival.eventplace", target = "eventplace")
    @Mapping(source = "detailFestival.usetimefestival", target = "usetimefestival")
    @Mapping(source = "detailFestival.playtime", target = "playtime")
    FestivalDto.Response festivalToResponse(Festival festival);

    @Mapping(source = "detailFestival.eventDate", target = "eventDate")
    FestivalDto.ListResponse festivalToListResponse(Festival festival);
    List<FestivalDto.ListResponse> festivalsToFestivalListResponses(List<Festival> festivals);
}
