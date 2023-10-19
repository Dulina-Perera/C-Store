package com.cstore.model.cart;

import lombok.*;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class CartItem {

    private Long userId;
    private Long variantId;
    private Integer count;

}
