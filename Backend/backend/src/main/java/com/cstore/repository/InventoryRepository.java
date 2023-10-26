package com.cstore.repository;

import com.cstore.model.warehouse.Inventory_;
import com.cstore.model.warehouse.InventoryId;
import com.cstore.model.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory_, InventoryId> {
    List<Inventory_> findByWarehouse(Warehouse warehouse);
}
