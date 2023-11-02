package com.cstore.model.order;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class OrderItem {
    private Long orderId;
    private Long variantId;
    private Long warehouseId;
    private Integer quantity;
    private BigDecimal price;
}
