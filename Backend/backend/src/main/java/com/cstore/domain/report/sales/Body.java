package com.cstore.domain.report.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Body {
    private Short year;
    private Short quarter;
    private Integer totalSales;
    private BigDecimal totalEarnings;

    private List<Variant> variants;
}
