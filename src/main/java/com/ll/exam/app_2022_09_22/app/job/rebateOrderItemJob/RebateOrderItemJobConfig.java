package com.ll.exam.app_2022_09_22.app.job.rebateOrderItemJob;

import com.ll.exam.app_2022_09_22.app.cart.service.CartService;
import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.member.service.MemberService;
import com.ll.exam.app_2022_09_22.app.order.entity.OrderItem;
import com.ll.exam.app_2022_09_22.app.order.repository.OrderItemRepository;
import com.ll.exam.app_2022_09_22.app.order.service.OrderService;
import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import com.ll.exam.app_2022_09_22.app.product.service.ProductService;
import com.ll.exam.app_2022_09_22.app.rebate.entity.RebatedOrderItem;
import com.ll.exam.app_2022_09_22.app.rebate.repository.RebatedOrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RebateOrderItemJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;
    private final OrderItemRepository orderItemRepository;
    private final RebatedOrderItemRepository rebatedOrderItemRepository;

    @Bean
    @Order(1000000000)
    public Job rebateOrderItemJob(Step rebateOrderItemJobStep1, Step rebateOrderItemJobStep2) {
        return jobBuilderFactory.get("rebateOrderItemJob")
                .start(rebateOrderItemJobStep1)
                .next(rebateOrderItemJobStep2)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @JobScope
    @Bean
    public Step rebateOrderItemJobStep1(Tasklet rebateOrderItemJobStep1Tasklet) {
        return stepBuilderFactory.get("rebateOrderItemJobStep1")
                .tasklet(rebateOrderItemJobStep1Tasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet rebateOrderItemJobStep1Tasklet(MemberService memberService, ProductService productService, CartService cartService, OrderService orderService) {
        return (contribution, chunkContext) -> {
            String password = "{noop}1234";
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");
            Member member3 = memberService.join("user3", password, "user3@test.com");
            Member member4 = memberService.join("user4", password, "user4@test.com");

            Product product1 = productService.create("단가라 OPS", 68000, 45000, "청평화 A-1-15", Arrays.asList(new ProductOption("RED", "44"), new ProductOption("RED", "55"), new ProductOption("BLUE", "44"), new ProductOption("BLUE", "55")));
            Product product2 = productService.create("쉬폰 OPS", 72000, 55000, "청평화 A-1-15", Arrays.asList(new ProductOption("BLACK", "44"), new ProductOption("BLACK", "55"), new ProductOption("WHITE", "44"), new ProductOption("WHITE", "55")));

            ProductOption productOption__RED_44 = product1.getProductOptions().get(0);
            ProductOption productOption__BLUE_44 = product1.getProductOptions().get(2);

            cartService.addItem(member1, productOption__RED_44, 1); // productOption__RED_44 총 수량 1
            cartService.addItem(member1, productOption__RED_44, 2); // productOption__RED_44 총 수량 3
            cartService.addItem(member1, productOption__BLUE_44, 1); // productOption__BLUE_44 총 수량 1

            orderService.createFromCart(member1);

            return RepeatStatus.FINISHED;
        };
    }

    @JobScope
    @Bean
    public Step rebateOrderItemJobStep2(
            ItemReader orderItemReader,
            ItemProcessor orderItemProcessor,
            ItemWriter orderItemWriter) {

        return stepBuilderFactory.get("rebateOrderItemJobStep2")
                .<OrderItem, RebatedOrderItem>chunk(1)
                .reader(orderItemReader)
                .processor(orderItemProcessor)
                .writer(orderItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<OrderItem> orderItemReader() {


        return new RepositoryItemReaderBuilder<OrderItem>()
                .name("orderItemReader")
                .repository(orderItemRepository)
                .methodName("findAll")
                .pageSize(1)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<OrderItem, RebatedOrderItem> orderItemProcessor() {
        return orderItem -> new RebatedOrderItem(orderItem);
    }

    @StepScope
    @Bean
    public ItemWriter<RebatedOrderItem> orderItemWriter() {
        return items -> items.forEach(item -> rebatedOrderItemRepository.save(item));
    }

}

