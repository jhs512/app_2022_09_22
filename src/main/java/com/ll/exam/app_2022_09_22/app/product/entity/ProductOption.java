package com.ll.exam.app_2022_09_22.app.product.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import lombok.*;
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
public class ProductOption extends BaseEntity {
    private String color;
    private String size;
    private int price;
    @ManyToOne(fetch = LAZY)
    private Product product;
    private boolean isSoldOut; // 사입처에서의 품절여부
    private int stockQuantity; // 쇼핑몰에서 보유한 물건 개수

    public ProductOption(String color, String size) {
        this.color = color;
        this.size = size;
    }
}
