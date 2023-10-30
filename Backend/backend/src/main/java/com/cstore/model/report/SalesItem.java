package com.cstore.model.report;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class SalesItem {
    private Short year;
    private Short quarter;
    private Long variantId;
    private Integer sales;
    private BigDecimal earnings;
}
