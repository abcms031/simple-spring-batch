package com.abcms031.member.batch.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;

    public static final String JOB_NAME = "MemberManagementBatch";

    @Bean
    public Job stepNextJob(@Qualifier("memberStep")Step memberStep,
                           @Qualifier("lendStep")Step lendStep,
                           @Qualifier("lateFeeStep")Step lateFeeStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .start(memberStep)
                .next(lendStep)
                .next(lateFeeStep)
                .build();
    }


}
