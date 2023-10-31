package com.cstore.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Token {
    private Long userId;
    private String content;
    private Boolean revoked;
    private Boolean expired;
}
