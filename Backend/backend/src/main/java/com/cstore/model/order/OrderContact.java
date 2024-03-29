package com.cstore.model.order;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "order_contact", schema = "cstore")
public class OrderContact {
    @EmbeddedId
    private OrderContactId orderContactId;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderContactId getOrderContactId() {
        return orderContactId;
    }

    public void setOrderContactId(OrderContactId orderContactId) {
        this.orderContactId = orderContactId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
