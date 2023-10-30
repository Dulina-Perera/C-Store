package com.cstore.dto;

import com.cstore.domain.product.select.PropertySelectionMap;
import com.cstore.dto.ProductSelectionCategory;
import com.cstore.model.product.Image;
import com.cstore.model.product.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedProduct {
    private Long productId;
    private String productName;
    private BigDecimal basePrice;
    private String brand;
    private String description;
    private String imageUrl;

    private List<Image> otherImages;

    private List<ProductSelectionCategory> categories;

    private List<Property> properties;
    // private PropertySelectionMap properties;

    private Integer stockCount;
}
