package com.cstore.domain.order.checkout;

import com.cstore.dao.inventory.InventoryDao;
import com.cstore.dao.order.OrderDao;
import com.cstore.exception.sparsestocks.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.init.ScriptParseException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static com.google.common.base.Throwables.getRootCause;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final InventoryDao inventoryDao;
    private final OrderDao orderDao;

    public Long placeOrder(
        Long userId
    ) throws DataAccessException, SparseStocksException, SQLIntegrityConstraintViolationException {
        Long orderId = orderDao.placeOrder(userId);

        try {
            inventoryDao.updateInventory(userId, orderId);
            return orderId;
        } catch (
            DataAccessException dae
        ) {
            if (dae.getMessage().startsWith("For variant with id")) {
                throw new SparseStocksException("Not enough stocks!");
            } else {
                throw dae;
            }
        }
    }
}
