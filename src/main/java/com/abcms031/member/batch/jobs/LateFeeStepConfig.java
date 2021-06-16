package com.abcms031.member.batch.jobs;

import com.abcms031.member.batch.domain.LateFee;
import com.abcms031.member.batch.repository.LateFeeRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


/**
 * 반납 안한 연체자들 1일마다 연체료 증가
 *
 * @author abcms031
 *
 */

@Configuration
@Slf4j
@AllArgsConstructor
public class LateFeeStepConfig {
    public static final String STEP_NAME = "lateFeeStep";
    private final StepBuilderFactory stepBuilderFactory;
    private final LateFeeRepository lateFeeRepository;


    @Bean(STEP_NAME)
    public Step lateFeeStep(){
        log.info("lateFeeStep start------------------");

        return stepBuilderFactory.get(STEP_NAME)
                .<LateFee, LateFee>chunk(10)
                .reader(lateFeeReader())
                .processor(updateLateFeeProcessor())
                .writer(updateLateFeeWriter())
                .build();
    }

    @Bean
    public ListItemReader<LateFee> lateFeeReader() {
        log.info("lateFeeReader start:::");
        List<LateFee> lateFeeList = lateFeeRepository.findAllByDeleted(false);
        log.info("lateFeeList size : " + lateFeeList.size());
        return new ListItemReader<>(lateFeeList);
    }

    @Bean
    public ItemProcessor<LateFee, LateFee> updateLateFeeProcessor() {
        log.info("updateLateFeeProcessor start:::");
        return item -> {
            item.setAmount(item.getAmount()+500);
            return item;
        } ;
    }

    @Bean
    public ItemWriter<LateFee> updateLateFeeWriter(){
        log.info("updateLateFeeWriter start:::");
        return ((List<? extends LateFee> lateFeeList) ->
                lateFeeRepository.saveAll(lateFeeList));
    }

    private BigDecimal getLateFee(LocalDate dueDate){
        log.info("getLateFee start:::");
        LocalDate now = LocalDate.now();
        Period period = Period.between(dueDate,now);
        BigDecimal amount = BigDecimal.valueOf(500*period.getDays());
        log.info("getLateFee start:::"+amount);
        return BigDecimal.valueOf(500*period.getDays());
    }

}
