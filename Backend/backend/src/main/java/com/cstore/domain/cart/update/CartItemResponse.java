package com.cstore.domain.cart.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class CartItemResponse {
    private Long variantId;
    private String productName;
    private String mainImage;
    private BigDecimal variantPrice;
    private Integer quantity;
}
