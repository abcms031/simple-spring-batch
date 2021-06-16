package com.abcms031.member.batch.jobs;
;
import com.abcms031.member.batch.domain.Member;
import com.abcms031.member.batch.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

/**
 * 1년 이상 로그인 안한 계정은 휴면계정으로 변경
 *
 * @author abcms031
 *
 */

@Configuration
@Slf4j
@AllArgsConstructor
public class MemberStepConfig {
    public static final String STEP_NAME = "memberStep";
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberRepository memberRepository;

    @Bean(STEP_NAME)
    public Step memberStep(){
        log.info("memberStep start------------------");

        return stepBuilderFactory.get(STEP_NAME)
                .<Member, Member>chunk(10)
                .reader(memberReader())
                .processor(memberProcessor())
                .writer(memberWriter())
                .build();
    }

    @Bean
    public ListItemReader<Member> memberReader() {
        log.info("memberReader start:::");
        LocalDate validDate = LocalDate.now().minusYears(1);
        List<Member> activeMembers = memberRepository.findAllByActiveAndLastLoginDateBefore(true, validDate);
        log.info("activeMembers size : " + activeMembers.size());
        return new ListItemReader<>(activeMembers);
    }

    @Bean
    public ItemProcessor<Member, Member> memberProcessor() {
        log.info("memberProcessor start:::");
        return item -> {
            item.setActive(false);
            return item;
        } ;
    }

    @Bean
    public ItemWriter<Member> memberWriter(){
        log.info("memberWriter start:::");
        return ((List<? extends Member> memberList) ->
                memberRepository.saveAll(memberList));
    }


}
