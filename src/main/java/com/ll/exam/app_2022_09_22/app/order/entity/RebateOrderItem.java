package com.ll.exam.app_2022_09_22.app.order.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
    private OrderItem orderItem;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = LAZY)
    private ProductOption productOption;

    @Embedded
    private RebateOrderItem.EmbMember embMember;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "isSoldOut", column = @Column(name = "product_option_is_soldout")),
            @AttributeOverride(name = "price", column = @Column(name = "product_option_price")),
            @AttributeOverride(name = "salePrice", column = @Column(name = "product_option_sale_price")),
            @AttributeOverride(name = "wholesalePrice", column = @Column(name = "product_option_wholesale_price"))
    })
    private RebateOrderItem.EmbProductOption embProductOption;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "isSoldOut", column = @Column(name = "product_is_soldout")),
            @AttributeOverride(name = "price", column = @Column(name = "product_price")),
            @AttributeOverride(name = "salePrice", column = @Column(name = "product_sale_price")),
            @AttributeOverride(name = "wholesalePrice", column = @Column(name = "product_wholesale_price"))
    })
    private EmbProduct embProduct;

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

    public RebateOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        order = orderItem.getOrder();
        productOption = orderItem.getProductOption();
        quantity = orderItem.getQuantity();
        price = orderItem.getPrice();
        salePrice = orderItem.getSalePrice();
        wholesalePrice = orderItem.getWholesalePrice();
        pgFee = orderItem.getPgFee();
        payPrice = orderItem.getPrice();
        refundPrice = orderItem.getRefundPrice();
        refundQuantity = orderItem.getRefundQuantity();
        isPaid = orderItem.isPaid();

        Member member = orderItem.getOrder().getMember();

        embMember = new EmbMember(member);
        embProductOption = new EmbProductOption(productOption);
        embProduct = new EmbProduct(productOption.getProduct());
    }

    public RebateOrderItem(ProductOption productOption, int quantity) {
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice();
        this.salePrice = productOption.getSalePrice();
        this.wholesalePrice = productOption.getWholesalePrice();
    }

    public int calculatePayPrice() {
        return salePrice * quantity;
    }

    public void setPaymentDone() {
        this.pgFee = 0;
        this.payPrice = calculatePayPrice();
        this.isPaid = true;
    }

    public void setRefundDone() {
        if (refundQuantity == quantity) return;

        this.refundQuantity = quantity;
        this.refundPrice = payPrice;
    }

    @Embeddable
    @NoArgsConstructor
    public static class EmbMember {
        @OneToOne(fetch = LAZY)
        private Member member;
        private String username;
        private String email;

        public EmbMember(Member member) {
            this.member = member;
            username = member.getUsername();
            email = member.getEmail();
        }
    }

    @Embeddable
    @NoArgsConstructor
    public static class EmbProductOption {
        private String color;
        private String size;
        private String displayColor;
        private String displaySize;
        private int price;
        private int salePrice;
        private int wholesalePrice;
        private boolean isSoldOut;

        public EmbProductOption(ProductOption productOption) {
            color = productOption.getColor();
            size = productOption.getSize();
            displayColor = productOption.getDisplayColor();
            displaySize = productOption.getDisplaySize();
            price = productOption.getPrice();
            salePrice = productOption.getSalePrice();
            wholesalePrice = productOption.getWholesalePrice();
            isSoldOut = productOption.isSoldOut();
        }
    }

    @Embeddable
    @NoArgsConstructor
    public static class EmbProduct {

        private int salePrice;
        private int price;
        private int wholesalePrice;

        private String name;
        private String makerShopName;

        private boolean isSoldOut;

        public EmbProduct(Product product) {
            salePrice = product.getSalePrice();
            price = product.getPrice();
            wholesalePrice = product.getWholesalePrice();
            name = product.getName();
            makerShopName = product.getMakerShopName();
            isSoldOut = product.isSoldOut();
        }
    }
}
