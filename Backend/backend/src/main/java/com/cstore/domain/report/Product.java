package com.cstore.domain.report;

import lombok.*;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Product {
    private Long productId;
    private String productName;
    private String mainImage;
    private Integer sales;
}
