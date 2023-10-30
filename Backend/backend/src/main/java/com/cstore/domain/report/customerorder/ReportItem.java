package com.cstore.domain.report.customerorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class ReportItem {
    private Long orderId;

    private String status;

    private Timestamp date;
    private BigDecimal totalPayment;
    private String paymentMethod;
    private String deliveryMethod;

    private ShippingAddress shippingAddress;

    private List<ReportVariant> variants;
}
