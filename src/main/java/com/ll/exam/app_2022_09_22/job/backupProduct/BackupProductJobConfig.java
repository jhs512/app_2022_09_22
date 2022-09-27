package com.ll.exam.app_2022_09_22.job.backupProduct;

import com.ll.exam.app_2022_09_22.app.product.entity.BackupedProduct;
import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.repository.BackupedProductRepository;
import com.ll.exam.app_2022_09_22.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
public class BackupProductJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;
    private final ProductRepository productRepository;
    private final BackupedProductRepository backupedProductRepository;

    @Bean
    public Job backupProductJob(Step backupProductJobStep1, CommandLineRunner initData) throws Exception {
        initData.run();

        return jobBuilderFactory.get("backupProductJob")
                //.incrementer(new RunIdIncrementer()) // 강제로 매번 다른 ID를 실행시에 파라미터로 부여
                .start(backupProductJobStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step backupProductJobStep1(
            ItemReader<Product> productReader,
            ItemProcessor<Product, BackupedProduct> productToBackpuedProductProcessor,
            ItemWriter<BackupedProduct> backpuedProductWriter
    ) {
        return stepBuilderFactory.get("backupProductJobStep1")
                .<Product, BackupedProduct>chunk(1)
                .reader(productReader)
                .processor(productToBackpuedProductProcessor)
                .writer(backpuedProductWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Product> productReader() {


        return new RepositoryItemReaderBuilder<Product>()
                .name("productReader")
                .repository(productRepository)
                .methodName("findAll")
                .pageSize(1)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<Product, BackupedProduct> productToBackpuedProductProcessor() {
        return product -> new BackupedProduct(product);
    }

    @StepScope
    @Bean
    public ItemWriter<BackupedProduct> backpuedProductWriter() {
        return items -> items.forEach(item -> backupedProductRepository.save(item));
    }
}
