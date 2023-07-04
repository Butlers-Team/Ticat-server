package Butlers.Ticat.festival.mapper;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.Festival;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FestivalMapper {

    @Mapping(source = "detailFestival.eventstartdate", target = "eventstartdate")
    @Mapping(source = "detailFestival.eventenddate", target = "eventenddate")
    @Mapping(source = "detailFestival.overview", target = "overview")
    @Mapping(source = "detailFestival.eventhomepage", target = "eventhomepage")
    @Mapping(source = "detailFestival.eventplace", target = "eventplace")
    @Mapping(source = "detailFestival.usetimefestival", target = "price")
    @Mapping(source = "detailFestival.playtime", target = "playtime")
    @Mapping(source = "detailFestival.status", target = "status")
    FestivalDto.Response festivalToResponse(Festival festival);

    @Mapping(source = "detailFestival.eventstartdate", target = "eventstartdate")
    @Mapping(source = "detailFestival.eventenddate", target = "eventenddate")
    @Mapping(source = "detailFestival.category", target = "category")
    @Mapping(source = "detailFestival.status", target = "status")
    FestivalDto.ListResponse festivalToListResponse(Festival festival);
    List<FestivalDto.ListResponse> festivalsToFestivalListResponses(List<Festival> festivals);
}
