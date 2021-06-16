package com.abcms031.member.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "LATE_FEE")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LateFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LATE_FEE_SEQ")
    private Long lateFeeSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LEND_SEQ")
    private Lend lendSeq;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "DELETED")
    private Boolean deleted;


}
