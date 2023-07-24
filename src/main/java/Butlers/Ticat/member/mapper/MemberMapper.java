package Butlers.Ticat.member.mapper;


import Butlers.Ticat.calendar.dto.CalendarDto;
import Butlers.Ticat.calendar.entity.Calendar;

import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;

import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.entity.MemberRecent;
import Butlers.Ticat.stamp.dto.StampDto;
import Butlers.Ticat.stamp.entity.Stamp;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    default Member memberPostToMember(MemberDto.Post requestBody) {
        // 입력한 두 비밀번호가 일치하는지 확인하는 로직
        if(!requestBody.getPassword().equals(requestBody.getConfirmPassword())) {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_DOES_NOT_MATCH);
        }

        Member member = new Member();
        member.setId(requestBody.getId());
        member.setEmail(requestBody.getEmail());
        member.setPassword(requestBody.getPassword());
        member.setDisplayName(requestBody.getDisplayName());

        return member;
    }

    default MemberDto.Response memberToMemberResponse(Member member) {
        return MemberDto.Response.builder()
                .memberId(member.getMemberId())
                .displayName(member.getDisplayName())
                .email(member.getEmail())
                .profileUrl(member.getProfileUrl())
                .pureProfileUrl(member.getPureProfileUrl())
                .build();
    }

    Member memberPatchToMember(MemberDto.Patch requestBody);


    default List<CalendarDto.CalendarResponse> getCalendarResponses(List<Calendar> calendars) {
        return calendars.stream()
                .map(calendar -> CalendarDto.CalendarResponse.builder()
                        .festivalId(calendar.getFestival().getFestivalId())
                        .calendarId(calendar.getCalenderId())
                        .status(calendar.getFestival().getDetailFestival().getStatus())
                        .category(calendar.getFestival().getDetailFestival().getCategory())
                        .scheduledDate(calendar.getScheduleDate())
                        .calendarDate(calendar.getCalendarDate())
                        .title(calendar.getFestival().getTitle())
                        .address(calendar.getFestival().getAddress())
                        .eventStartDate(calendar.getFestival().getDetailFestival().getEventstartdate())
                        .eventEndDate(calendar.getFestival().getDetailFestival().getEventenddate())
                        .build())
                .collect(Collectors.toList());
    }


    default List<StampDto.StampResponse> getStampResponses(List<Stamp> stamps) {
        return stamps.stream()
                .map(stamp -> StampDto.StampResponse.builder()
                        .festivalId(stamp.getFestival().getFestivalId())
                        .stampId(stamp.getStampId())
                        .status(stamp.getFestival().getDetailFestival().getStatus())
                        .category(stamp.getFestival().getDetailFestival().getCategory())
                        .stampDate(stamp.getStampDate())
                        .title(stamp.getFestival().getTitle())
                        .address(stamp.getFestival().getAddress())
                        .eventStartDate(stamp.getFestival().getDetailFestival().getEventstartdate())
                        .eventEndDate(stamp.getFestival().getDetailFestival().getEventenddate())
                        .build())
                .collect(Collectors.toList());
    }

    default MemberDto.ProfileUrl memberToProfileUrl(Member member) {
        return MemberDto.ProfileUrl.builder()
                .profileUrl(member.getProfileUrl()).build();
    }

    default List<MemberDto.recentFestivalResponse> getRecentResponses(List<MemberRecent> recents, Member member) {
        Set<Long> favoriteFestivalIds = member.getFavorites().stream()
                .map(favorite -> favorite.getFestival().getFestivalId())
                .collect(Collectors.toSet());

        return recents.stream()
                .map(recent -> MemberDto.recentFestivalResponse.builder()
                        .festivalId(recent.getFestival().getFestivalId())
                        .isFavorite(favoriteFestivalIds.contains(recent.getFestival().getFestivalId()))
                        .title(recent.getFestival().getTitle())
                        .address(recent.getFestival().getAddress())
                        .eventStartDate(recent.getFestival().getDetailFestival().getEventstartdate())
                        .eventEndDate(recent.getFestival().getDetailFestival().getEventenddate())
                        .imageUrl(recent.getFestival().getImage())
                        .build())
                .collect(Collectors.toList());
    }
}
