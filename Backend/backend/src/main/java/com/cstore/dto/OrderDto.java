package com.cstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDto {
    private Long orderId;
    private Timestamp date;
    private BigDecimal totalPayment;
    private String paymentMethod;
    private String deliveryMethod;
    private String email;
    private String streetNumber;
    private String streetName;
    private String city;
    private Integer zipCode;
    List<Integer> telephoneNumbers;
}
