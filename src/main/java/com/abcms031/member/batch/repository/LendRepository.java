package com.abcms031.member.batch.repository;

import com.abcms031.member.batch.domain.Lend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface LendRepository extends JpaRepository<Lend,Long>{

    List<Lend> findAllByDueDateBeforeAndReturned(LocalDate vaildCheckDate, Boolean returned);

}
