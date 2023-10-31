package com.cstore.model.product;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Image {
    private Long productId;
    private String url;
}
