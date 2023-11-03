package com.cstore.model.warehouse;

import lombok.*;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Inventory {
    private Long warehouseId;
    private Long variantId;
    private String sku;
    private Integer quantity;
}
