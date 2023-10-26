package com.cstore.service;

import com.cstore.dto.WarehouseVariantDTO;
import com.cstore.exception.NoSuchWarehouseException;
import com.cstore.model.warehouse.Inventory_;
import com.cstore.model.warehouse.Warehouse;
import com.cstore.repository.InventoryRepository;
import com.cstore.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Service
public class InventoryService {
    /*private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, WarehouseRepository warehouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    private WarehouseVariantDTO convert(Inventory_ inventory) {
        WarehouseVariantDTO warehouseVariantDTO = new WarehouseVariantDTO();

        warehouseVariantDTO.setVariantId(inventory.getInventoryId().getVariantId());
        warehouseVariantDTO.setSku(inventory.getSku());
        warehouseVariantDTO.setCount(inventory.getCount());

        return warehouseVariantDTO;
    }
    public List<WarehouseVariantDTO> getWarehouseVariants(Long warehouseId) {
        Optional<Warehouse> tempWarehouse = warehouseRepository.findById(warehouseId);

        if (tempWarehouse.isEmpty()) {
            throw new NoSuchWarehouseException("Warehouse with id " + warehouseId + " does not exist.");
        }
        Warehouse warehouse = tempWarehouse.get();

        return inventoryRepository.findByWarehouse(warehouse)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }*/
}
