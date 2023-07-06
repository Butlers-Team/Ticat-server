package Butlers.Ticat.member.mapper;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.stamp.entity.Stamp;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);

    MemberDto.Response memberToMemberResponse(Member member);

    Member memberPatchToMember(MemberDto.Patch requestBody);

    default List<FestivalDto.StampResponse> getResponses(List<Stamp> stamps) {
        return stamps.stream()
                .map(stamp -> FestivalDto.StampResponse.builder()
                        .contentId(stamp.getFestival().getContentId())
                        .status(stamp.getFestival().getDetailFestival().getStatus())
                        .stampDate(stamp.getStampDate())
                        .title(stamp.getFestival().getTitle())
                        .address(stamp.getFestival().getAddress())
                        .eventStartDate(stamp.getFestival().getDetailFestival().getEventstartdate())
                        .eventEndDate(stamp.getFestival().getDetailFestival().getEventenddate())
                        .build())
                .collect(Collectors.toList());
    }
}
