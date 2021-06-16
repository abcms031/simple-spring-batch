package com.abcms031.member.batch.repository;

import com.abcms031.member.batch.domain.LateFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LateFeeRepository extends JpaRepository<LateFee,Long> {

    List<LateFee> findAllByDeleted(Boolean deleted);

}
