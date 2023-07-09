package Butlers.Ticat.member.mapper;

import Butlers.Ticat.calendar.dto.CalendarDto;
import Butlers.Ticat.calendar.entity.Calendar;
import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);

    MemberDto.Response memberToMemberResponse(Member member);

    Member memberPatchToMember(MemberDto.Patch requestBody);

    default List<CalendarDto.CalendarResponse> getResponses(List<Calendar> calendars) {
        return calendars.stream()
                .map(calendar -> CalendarDto.CalendarResponse.builder()
                        .contentId(calendar.getFestival().getContentId())
                        .staus(calendar.getFestival().getDetailFestival().getStatus())
                        .calendarDate(calendar.getCalendarDate())
                        .title(calendar.getFestival().getTitle())
                        .address(calendar.getFestival().getAddress())
                        .eventStartDate(calendar.getFestival().getDetailFestival().getEventstartdate())
                        .eventEndDate(calendar.getFestival().getDetailFestival().getEventenddate())
                        .build())
                .collect(Collectors.toList());
    }

}
