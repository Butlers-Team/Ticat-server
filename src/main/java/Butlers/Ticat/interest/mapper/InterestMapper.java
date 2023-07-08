package Butlers.Ticat.interest.mapper;

import Butlers.Ticat.interest.dto.InterestDto;
import Butlers.Ticat.interest.entity.Interest;
import Butlers.Ticat.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterestMapper {
    default Interest postToInterest(Long memberId, InterestDto.Post requestBody) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setDisplayName(requestBody.getDisplayName());

        Interest interest = new Interest();
        interest.setMember(member);
        interest.setCategories(requestBody.getCategories());

        return interest;
    }

    default Interest patchToInterest(Long memberId, InterestDto.Patch requestBody) {
        Member member = new Member();
        member.setMemberId(memberId);

        Interest interest = new Interest();
        interest.setMember(member);
        interest.setCategories(requestBody.getCategories());

        return interest;
    }
}
