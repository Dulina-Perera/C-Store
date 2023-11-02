package com.cstore.domain.user;

import com.cstore.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class RegUser {
    private Long userId;
    private Role role;
    private String firstName;
    private String lastName;

    private List<String> telephoneNumbers;

    private List<Address> addresses;
}
