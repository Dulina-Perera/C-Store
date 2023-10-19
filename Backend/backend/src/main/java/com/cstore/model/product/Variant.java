package com.cstore.model.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Entity @Table(name = "variant", schema = "cstore")
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Variant {
    @Id @Column(name = "variant_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "variantIdGenerator", sequenceName = "variantIdSequence",
        allocationSize = 1, initialValue = 1
    )
    private Long variantId;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Variant variant = (Variant) o;
        return getVariantId() != null && Objects.equals(getVariantId(), variant.getVariantId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
