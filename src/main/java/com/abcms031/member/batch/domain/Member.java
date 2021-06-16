package com.abcms031.member.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "MEMBER")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SEQ")
    private Long id;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_PASSWORD")
    private String memberPassword;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "LAST_LOGIN_DATE")
    private LocalDate lastLoginDate;

    @Column(name = "ACTIVE")
    private Boolean active;


}
