package com.cstore.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class RegUser {
    private Long userId;
    private String firstName;
    private String lastName;

    private List<String> telephoneNumbers;

    private List<Address> addresses;
}
