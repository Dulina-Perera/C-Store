package com.cstore.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class ModifiedOrder {
    private Long orderId;

    private String status;
    private Timestamp date;

    private BigDecimal totalPayment;
    private String paymentMethod;

    private String deliveryMethod;
    private Date deliveryEstimate;

    private String email;

    private String streetNumber;
    private String streetName;
    private String city;
    private Integer zipcode;
}
