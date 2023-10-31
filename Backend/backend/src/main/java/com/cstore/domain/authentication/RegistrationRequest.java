package com.cstore.domain.authentication;

import com.cstore.dto.UserAddressDto;
import lombok.*;

import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UserAddressDto> addresses;
    private List<String> telephoneNumbers;
}
