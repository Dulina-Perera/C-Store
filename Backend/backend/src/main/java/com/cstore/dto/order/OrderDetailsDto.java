package com.cstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDetailsDto {
    private Timestamp date;
    private BigDecimal totalPayment;
    private String paymentMethod;
    private String deliveryMethod;

    private String email;
    private String streetNumber;
    private String streetName;
    private String city;
    private Integer zipcode;

    private String telephoneNumber;
}
