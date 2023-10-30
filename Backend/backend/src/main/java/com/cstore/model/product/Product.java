package com.cstore.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity @Table(name = "product", schema = "cstore")
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@ToString
public class Product {
    @Id @Column(
        name = "product_id",
        nullable = false
    )
    @GeneratedValue(
        generator = "productIdGenerator",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "productIdGenerator", sequenceName = "productIdSequence",
        allocationSize = 1, initialValue = 1
    )
    private Long productId;

    @Column(name = "product_name", length = 100)
    private String productName;

    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "brand", length = 40)
    private String brand;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String mainImage;
}
