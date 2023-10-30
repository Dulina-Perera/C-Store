package com.cstore.model.report;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class SalesReport {
    private Short year;
    private Short quarter;
    private Integer totalSales;
    private BigDecimal totalEarnings;
}
