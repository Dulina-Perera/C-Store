package com.cstore.domain.report;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class OrderItemProperty {
    private long variantId;
    private String productName;
    private String propertyName;
    private String value;
    private Integer quantity;
    private BigDecimal price;
}
