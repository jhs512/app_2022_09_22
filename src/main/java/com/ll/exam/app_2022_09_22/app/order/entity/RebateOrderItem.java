package com.ll.exam.app_2022_09_22.app.order.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class RebateOrderItem extends BaseEntity {
    @OneToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OrderItem orderItem;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductOption productOption;

    private int quantity;

    // 가격
    private int price; // 권장판매가
    private int salePrice; // 실제판매가
    private int wholesalePrice; // 도매가
    private int pgFee; // 결제대행사 수수료
    private int payPrice; // 결제금액
    private int refundPrice; // 환불금액
    private int refundQuantity; // 환불한 개수
    private boolean isPaid; // 결제여부

    // 상품
    private String productName;

    // 상품옵션
    private String productOptionColor;
    private String productOptionSize;
    private String productOptionDisplayColor;
    private String productOptionDisplaySize;

    // 주문품목
    private LocalDateTime orderItemCreateDate;

    public RebateOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        order = orderItem.getOrder();
        productOption = orderItem.getProductOption();
        quantity = orderItem.getQuantity();
        price = orderItem.getPrice();
        salePrice = orderItem.getSalePrice();
        wholesalePrice = orderItem.getWholesalePrice();
        pgFee = orderItem.getPgFee();
        payPrice = orderItem.getPayPrice();
        refundPrice = orderItem.getRefundPrice();
        refundQuantity = orderItem.getRefundQuantity();
        isPaid = orderItem.isPaid();

        // 상품 추가데이터
        productName = orderItem.getProductOption().getProduct().getName();

        // 상품 옵션 추가데이터
        productOptionColor = orderItem.getProductOption().getColor();
        productOptionSize = orderItem.getProductOption().getSize();
        productOptionDisplayColor = orderItem.getProductOption().getDisplayColor();
        productOptionDisplaySize = orderItem.getProductOption().getDisplaySize();

        // 주문품목 추가데이터
        orderItemCreateDate = orderItem.getCreateDate();
    }
}
