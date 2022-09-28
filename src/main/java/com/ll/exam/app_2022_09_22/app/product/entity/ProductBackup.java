package com.ll.exam.app_2022_09_22.app.product.entity;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ProductBackup extends BaseEntity {
    private int salePrice;
    private int price;
    private int wholesalePrice;
    private String name;
    private String makerShopName;
    private boolean isSoldOut; // 관련 옵션들이 전부 판매불능 상태일 때

    @OneToOne(fetch = LAZY)
    private Product product;

    public ProductBackup(Product product) {
        this.product = product;
        salePrice = product.getSalePrice();
        price = product.getPrice();
        wholesalePrice = product.getWholesalePrice();
        name = product.getName();
        makerShopName = product.getMakerShopName();
        isSoldOut = product.isSoldOut();
    }
}
