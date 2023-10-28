package com.cstore.domain.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class OrderReport {
    private String name;
    private Long orderId;
    private Timestamp date;
    private BigDecimal totalPayment;
    private String paymentMethod;
    private String deliveryMethod;
}
