package com.cstore.domain.order.checkout;

import com.cstore.dao.inventory.InventoryDao;
import com.cstore.dao.order.OrderDao;
import com.cstore.exception.sparsestocks.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        Long orderId = orderDao.placeOrder(userId);

        try {
            inventoryDao.updateInventory(userId, orderId);
            return orderId;
        } catch (
            RuntimeException e
        ) {
            orderDao.deleteByID(orderId);

            Throwable rootCause = getRootCause(e);
            if (rootCause instanceof SQLException) {
                /*
                 * When no warehouse has at least one variant the customer requests.
                 */
                if ("23502".equals(((SQLException) rootCause).getSQLState())) {
                    throw new SparseStocksException("Some user requested variants are out of stock.");
                }
            }
            throw new SparseStocksException("Not enough stocks.");
        }
    }
}
