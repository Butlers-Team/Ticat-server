package Butlers.Ticat.stamp.mapper;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.stamp.dto.StampDto;
import Butlers.Ticat.stamp.entity.Location;
import Butlers.Ticat.stamp.entity.Stamp;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StampMapper {


    default StampDto.Response stampToResponse(Member member) {
        return StampDto.Response.builder()
                .memberId(member.getMemberId())
                .festivalList(getResponses(member.getStampList()))
                .build();
    }

    default List<FestivalDto.StampResponse> getResponses(List<Stamp> stamps) {
        return stamps.stream()
                .map(stamp -> FestivalDto.StampResponse.builder()
                        .contentId(stamp.getFestival().getContentId())
                        .title(stamp.getTitle())
                        .address(stamp.getFestival().getAddress())
                        .eventStartDate(stamp.)
                        .eventDate(stamp.getFestival().getDetailFestival().getEventDate())
                        .build())
                .collect(Collectors.toList());
    }
}
