package com.cstore.domain.report.customerorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class ShippingAddress {
    private String streetNumber;
    private String streetName;
    private String city;
    private Integer zipcode;
}
