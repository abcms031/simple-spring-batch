package com.abcms031.member.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "LEND")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEND_SEQ")
    private Long lendSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_SEQ")
    private Member memberSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOOK_SEQ")
    private Book bookSeq;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "RETURNED")
    private Boolean returned;
}
