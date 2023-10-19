package com.cstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class VariantProperiesDto {
    private List<Long> propertyIds;
    private Integer quantity;
}
