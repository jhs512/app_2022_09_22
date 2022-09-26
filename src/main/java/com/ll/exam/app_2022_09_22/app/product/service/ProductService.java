package com.ll.exam.app_2022_09_22.app.product.service;

import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void create(String name, int price, String makerShopName) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .makerShopName(makerShopName)
                .build();

        productRepository.save(product);
    }
}
