package Butlers.Ticat.member.mapper;

import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);

    MemberDto.Response memberToMemberResponse(Member member);

    Member memberPatchToMember(MemberDto.Patch requestBody);
}
