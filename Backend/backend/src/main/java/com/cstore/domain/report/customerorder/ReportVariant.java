package com.cstore.domain.report.customerorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class ReportVariant {
    private String productName;
    private Map<String, String> properties;
    private Integer quantity;
    private BigDecimal price;
}
