package com.cstore.domain.inventory;

import com.cstore.dao.inventory.InventoryDao;
import com.cstore.model.warehouse.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryDao dao;

    public Inventory stock(
        Inventory newStock
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        dao.stock(newStock);

        Optional<Inventory> inventory = dao.findByPrimaryKey(
            newStock.getWarehouseId(),
            newStock.getVariantId()
        );

        return inventory.get();
    }
}
