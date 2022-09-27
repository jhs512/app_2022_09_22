package com.ll.exam.app_2022_09_22.app.base;

import com.ll.exam.app_2022_09_22.app.cart.service.CartService;
import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.member.service.MemberService;
import com.ll.exam.app_2022_09_22.app.order.entity.Order;
import com.ll.exam.app_2022_09_22.app.order.service.OrderService;
import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import com.ll.exam.app_2022_09_22.app.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
@Slf4j
public class DevInitData {
    @Bean
    public CommandLineRunner initData(MemberService memberService, ProductService productService, CartService cartService, OrderService orderService) {
        return args ->
        {
            String password = "{noop}1234";
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");
            Member member3 = memberService.join("user3", password, "user3@test.com");
            Member member4 = memberService.join("user4", password, "user4@test.com");

            // 만원 충전
            memberService.addCash(member1, 10_000, "충전__무통장입금");
            // 이만원 충전
            memberService.addCash(member1, 20_000, "충전__무통장입금");
            // 5천원 사용
            memberService.addCash(member1, -5_000, "출금__일반");

            // 삼십만원 충전
            memberService.addCash(member1, 300_000, "충전__무통장입금");

            // 현재 보유중인 캐시 금액
            long restCash = memberService.getRestCash(member1);

            log.debug("member1 restCash : " + restCash);

            Product product1 = productService.create("단가라 OPS", 68000, 45000, "청평화 A-1-15", Arrays.asList(new ProductOption("RED", "44"), new ProductOption("RED", "55"), new ProductOption("BLUE", "44"), new ProductOption("BLUE", "55")));
            Product product2 = productService.create("쉬폰 OPS", 72000, 55000, "청평화 A-1-15", Arrays.asList(new ProductOption("BLACK", "44"), new ProductOption("BLACK", "55"), new ProductOption("WHITE", "44"), new ProductOption("WHITE", "55")));

            ProductOption product1Option__RED_44 = product1.getProductOptions().get(0);
            ProductOption product1Option__BLUE_44 = product1.getProductOptions().get(2);

            cartService.addItem(member1, product1Option__RED_44, 1); // productOption__RED_44 총 수량 1
            cartService.addItem(member1, product1Option__RED_44, 2); // productOption__RED_44 총 수량 3
            cartService.addItem(member1, product1Option__BLUE_44, 1); // productOption__BLUE_44 총 수량 1

            Order order1 = orderService.createFromCart(member1);

            int order1PayPrice = order1.calculatePayPrice();
            orderService.payByRestCashOnly(order1);

            // 2번 주문 생성
            // - 장바구니에 담기
            // - 주문 생성

            ProductOption product2Option__BLACK_44 = product2.getProductOptions().get(0);
            ProductOption product2Option__WHITE_44 = product2.getProductOptions().get(2);

            cartService.addItem(member2, product1Option__RED_44, 1);
            cartService.addItem(member2, product2Option__BLACK_44, 2);
            cartService.addItem(member2, product2Option__WHITE_44, 2);

            Order order2 = orderService.createFromCart(member2);

            log.debug("order2 payPrice : " + order2.calculatePayPrice());

            memberService.addCash(member2, 370_000, "충전__무통장입금");

            log.debug("member2 restCash : " + member2.getRestCash());

            orderService.payByRestCashOnly(order2);
        };
    }
}