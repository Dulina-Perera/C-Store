package com.cstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class PropertyDto {
    private Long propertyId;
    private String propertyName;
    private String value;
    private String imageUrl;
    private BigDecimal priceIncrement;
}
