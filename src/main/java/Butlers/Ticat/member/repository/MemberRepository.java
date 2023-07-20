package Butlers.Ticat.member.repository;

import Butlers.Ticat.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(String id);
    Optional<Member> findByEmailAndIsOauthChecked(String email, boolean checked);
}
