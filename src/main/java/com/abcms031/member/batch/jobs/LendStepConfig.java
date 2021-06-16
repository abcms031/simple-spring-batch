package com.abcms031.member.batch.jobs;

import com.abcms031.member.batch.domain.LateFee;
import com.abcms031.member.batch.domain.Lend;
import com.abcms031.member.batch.repository.LateFeeRepository;
import com.abcms031.member.batch.repository.LendRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

;

/**
 * 반납일자 넘긴 유저들은 연체 목록에 등록
 *
 * @author abcms031
 *
 */

@Configuration
@Slf4j
@AllArgsConstructor
public class LendStepConfig {

    public static final String STEP_NAME = "lendStep";
    private final StepBuilderFactory stepBuilderFactory;
    private final LendRepository lendRepository;
    private final LateFeeRepository lateFeeRepository;


    @Bean(STEP_NAME)
    public Step lendStep(){
        log.info("lendStep start------------------");

        return stepBuilderFactory.get(STEP_NAME)
                .<Lend, LateFee>chunk(10)
                .reader(lendReader())
                .processor(saveLateFeeProcessor())
                .writer(saveLateFeeWriter())
                .build();
    }

    @Bean
    public ListItemReader<Lend> lendReader() {
        log.info("lendReader start:::");
        LocalDate validDate = LocalDate.now();

        List<Lend> newOverdueList = lendRepository.findAllByDueDateBeforeAndReturned(validDate, false);
        log.info("newOverdueList size : " + newOverdueList.size());
        return new ListItemReader<>(newOverdueList);
    }
    @Bean
    public ItemProcessor<Lend, LateFee> saveLateFeeProcessor(){
        log.info("saveLateFeeProcessor start:::");
        return item -> {
            LateFee lateFee = new LateFee();
            lateFee.setAmount(0);
            lateFee.setLendSeq(item);
            lateFee.setDeleted(false);
            lateFee.setRegDate(LocalDateTime.now());
            return lateFee;
        };
    }

    @Bean
    public ItemWriter<LateFee> saveLateFeeWriter(){
        log.info("saveLateFeeWriter start:::");
        return ((List<? extends LateFee> lateFeeList) ->
                lateFeeRepository.saveAll(lateFeeList));
    }


}
