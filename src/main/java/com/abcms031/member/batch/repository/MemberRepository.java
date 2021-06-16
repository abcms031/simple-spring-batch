package com.abcms031.member.batch.repository;

import com.abcms031.member.batch.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findAllByActiveAndLastLoginDateBefore(Boolean active, LocalDate vaildDate);
}
