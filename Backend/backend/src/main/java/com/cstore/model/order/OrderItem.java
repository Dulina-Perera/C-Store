package com.cstore.model.order;

import com.cstore.model.product.Variant;
import com.cstore.model.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class OrderItem {
    private Long orderId;
    private Long variantId;
    private Long warehouseId;
    private Integer count;
    private BigDecimal price;
}
