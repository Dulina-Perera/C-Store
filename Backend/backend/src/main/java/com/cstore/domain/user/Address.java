package com.cstore.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Address {
    private String streetNumber;
    private String streetName;
    private String city;
    private Integer zipcode;
}
