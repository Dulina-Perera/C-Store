package com.cstore.dto;

import lombok.*;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class ProductSelectionCategory {
    private Long categoryId;
    private String categoryName;
}
