package Butlers.Ticat.member.mapper;

import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.stamp.dto.StampDto;
import Butlers.Ticat.stamp.entity.Stamp;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    default Member memberPostToMember(MemberDto.Post requestBody) {
        // 입력한 두 비밀번호가 일치하는지 확인하는 로직
        if(!requestBody.getPassword().equals(requestBody.getConfirmPassword())) {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_DOES_NOT_MATCH);
        }

        Member member = new Member();
        member.setEmail(requestBody.getEmail());
        member.setPassword(requestBody.getPassword());

        return member;
    }

    MemberDto.Response memberToMemberResponse(Member member);

    Member memberPatchToMember(MemberDto.Patch requestBody);

    default List<StampDto.StampResponse> getResponses(List<Stamp> stamps) {
        return stamps.stream()
                .map(stamp -> StampDto.StampResponse.builder()
                        .festivalId(stamp.getFestival().getFestivalId())
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
