package com.ll.exam.app_2022_09_22.app.product.entity;

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
public class BackupedProduct extends Product {
    @OneToOne(fetch = LAZY)
    private Product product;

    public BackupedProduct(Product product) {
        this.product = product;

        setSalePrice(product.getSalePrice());
        setPrice(product.getPrice());
        setWholesalePrice(product.getWholesalePrice());
        setName(product.getName());
        setMakerShopName(product.getMakerShopName());
        setSoldOut(product.isSoldOut());
    }
}
