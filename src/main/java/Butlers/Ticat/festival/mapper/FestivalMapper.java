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
    FestivalDto.ListResponse festivalToListResponse(Festival festival);
    List<FestivalDto.ListResponse> festivalsToFestivalListResponses(List<Festival> festivals);
}
