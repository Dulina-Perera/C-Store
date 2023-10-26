package com.cstore.domain.order.checkout;

import com.cstore.dao.inventory.InventoryDao;
import com.cstore.dao.order.OrderDao;
import com.cstore.exception.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

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
        } catch (SQLIntegrityConstraintViolationException sicve) {
            orderDao.deleteByID(orderId);
            throw new SparseStocksException("Not enough stocks.");
        }
    }
}
