package Butlers.Ticat.member.repository;

import Butlers.Ticat.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
