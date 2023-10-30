package com.cstore.domain.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Category {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private Integer orderCount;
}
