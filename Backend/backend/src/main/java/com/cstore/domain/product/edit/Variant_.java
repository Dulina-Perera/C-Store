package com.cstore.domain.product.edit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Variant_ {
    private BigDecimal weight;
    private List<Long> propertyIds;
}
