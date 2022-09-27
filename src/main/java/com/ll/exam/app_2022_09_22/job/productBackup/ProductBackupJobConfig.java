package com.ll.exam.app_2022_09_22.job.productBackup;

import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductBackup;
import com.ll.exam.app_2022_09_22.app.product.repository.ProductBackupRepository;
import com.ll.exam.app_2022_09_22.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductBackupJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ProductRepository productRepository;
    private final ProductBackupRepository productBackupRepository;


    @Bean
    public Job productBackupJob(Step productBackupStep1, CommandLineRunner initData) throws Exception {
        initData.run();

        return jobBuilderFactory.get("productBackupJob")
                .start(productBackupStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step productBackupStep1(
            ItemReader productReader,
            ItemProcessor productToProductBackupProcessor,
            ItemWriter productBackupWriter
    ) {
        return stepBuilderFactory.get("productBackupStep1")
                .<Product, ProductBackup>chunk(100)
                .reader(productReader)
                .processor(productToProductBackupProcessor)
                .writer(productBackupWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Product> productReader() {
        return new RepositoryItemReaderBuilder<Product>()
                .name("productReader")
                .repository(productRepository)
                .methodName("findAll")
                .pageSize(100)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Product, ProductBackup> productToProductBackupProcessor() {
        return product -> new ProductBackup(product);
    }

    @StepScope
    @Bean
    public ItemWriter<ProductBackup> productBackupWriter() {
        return items -> items.forEach(item -> {
            ProductBackup oldProductBackup = productBackupRepository.findByProductId(item.getProduct().getId()).orElse(null);

            if ( oldProductBackup != null ) {
                productBackupRepository.delete(oldProductBackup);
            }

            productBackupRepository.save(item);
        });
    }
}

