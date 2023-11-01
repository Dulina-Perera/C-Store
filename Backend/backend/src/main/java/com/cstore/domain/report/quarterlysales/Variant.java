package com.cstore.domain.report.quarterlysales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Variant {
    private String productName;
    private Map<String, String> properties;
    private Integer sales;
    private BigDecimal earnings;
}
