package com.cstore.dao.inventory;

import com.cstore.model.warehouse.Inventory;
import org.springframework.dao.DataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public interface InventoryDao {
    Optional<Inventory> findByPrimaryKey(Long warehouseId, Long variantId) throws DataAccessException;

    Integer findCountByVariantId(Long variantId);

    int stock(Inventory newStock) throws DataAccessException, SQLIntegrityConstraintViolationException;
}
