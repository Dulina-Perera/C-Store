package com.cstore.domain.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Quarter {
    private Short year;
    private Short quarter;
    private Integer sales;
    private BigDecimal earnings;
}
