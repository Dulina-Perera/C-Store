package com.cstore.domain.cart.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class CartItemUpdateRequest {
    private Long variantId;
    private Integer newCount;
}
