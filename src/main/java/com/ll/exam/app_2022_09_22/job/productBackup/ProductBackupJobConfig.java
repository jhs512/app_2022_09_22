package com.ll.exam.app_2022_09_22.job.productBackup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductBackupJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job productBackupJob(Step productBackupStep1, CommandLineRunner initData) throws Exception {
        initData.run();

        return jobBuilderFactory.get("productBackupJob")
                .start(productBackupStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step productBackupStep1(Tasklet productBackupStep1Tasklet) {
        return stepBuilderFactory.get("productBackupStep1")
                .tasklet(productBackupStep1Tasklet)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet productBackupStep1Tasklet() {
        return (contribution, chunkContext) -> {
            log.debug("productBackupStep1Tasklet 실행됨!");

            return RepeatStatus.FINISHED;
        };
    }
}

