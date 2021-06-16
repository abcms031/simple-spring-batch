package com.abcms031.member.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "BOOK")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_SEQ")
    private Long bookSeq;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "AVAILABLE")
    private Boolean available;


}
