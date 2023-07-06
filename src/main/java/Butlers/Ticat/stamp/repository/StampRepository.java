package Butlers.Ticat.stamp.repository;

import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.stamp.entity.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {
    Page<Stamp> findByMemberAndStampDateBetween(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
