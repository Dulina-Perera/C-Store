package com.cstore.model.order;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity @Table(name = "`order`", schema = "cstore")
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Order {
    @Id @Column(
        name = "order_id",
        nullable = false
    )
    @GeneratedValue(
        generator = "orderIdGenerator",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "orderIdGenerator",
        allocationSize = 1, initialValue = 1
    )
    private Long orderId;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "total_payment", precision = 12, scale = 2)
    private BigDecimal totalPayment;

    @Column(name = "payment_method", length = 20)
    private String paymentMethod;

    @Column(name = "delivery_method", length = 40)
    private String deliveryMethod;

    @Column(name = "email", length = 60)
    private String email;

    @Column(name = "street_number", length = 10)
    private String streetNumber;

    @Column(name = "street_name", length = 60)
    private String streetName;

    @Column(name = "city", length = 40)
    private String city;

    @Column(name = "zipcode")
    private Integer zipcode;
}
