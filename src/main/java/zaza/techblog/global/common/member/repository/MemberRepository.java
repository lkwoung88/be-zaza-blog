package zaza.techblog.global.common.member.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zaza.techblog.global.common.code.StatusCode;
import zaza.techblog.global.common.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndStatus(String id, StatusCode status);

    List<Member> findByStatusOrderByName(StatusCode status, Pageable pageable);

    int countByStatus(StatusCode status);
}
