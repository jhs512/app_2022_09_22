package com.ll.exam.app_2022_09_22.app.order.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = LAZY)
    private ProductOption productOption;

    private int quantity;

    public OrderItem(ProductOption productOption, int quantity) {
        this.productOption = productOption;
        this.quantity = quantity;
    }
}
