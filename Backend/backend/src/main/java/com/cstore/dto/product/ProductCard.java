package com.cstore.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class ProductCard {
    private Long productId;
    private String productName;
    private BigDecimal basePrice;
    private String brand;
    private String mainImage;
    private Map<String, List<String>> unmarketableProperties;
}
