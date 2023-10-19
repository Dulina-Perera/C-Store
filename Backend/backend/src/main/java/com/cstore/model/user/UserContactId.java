package com.cstore.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode
public class UserContactId implements Serializable {
    @Serial
    private static final long serialVersionUID = 4535620079918483570L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "telephone_number", nullable = false)
    private Integer telephoneNumber;
}
