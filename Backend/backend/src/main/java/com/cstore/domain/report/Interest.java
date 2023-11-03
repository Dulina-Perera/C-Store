package com.cstore.domain.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Interest {
    private String productName;
    private String brand;
    private String description;
    private String mainImage;

    private List<Quarter> quarters;
}
