package com.cstore.model.warehouse;

import com.cstore.model.product.Variant;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "inventory", schema = "cstore", indexes = {@Index(name = "variant_id", columnList = "variant_id")})
public class Inventory_ {
    @EmbeddedId
    private InventoryId inventoryId;

    @MapsId("warehouseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @MapsId("variantId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

    @Column(name = "sku", length = 20)
    private String sku;

    @Column(name = "count")
    private Integer count;

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
