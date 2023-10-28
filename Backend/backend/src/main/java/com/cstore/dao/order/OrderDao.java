package com.cstore.dao.order;

import com.cstore.model.order.Order;
import com.cstore.dto.order.OrderDetailsDto;
import org.springframework.dao.DataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<com.cstore.model.order.Order> findAll();

    List<Order> findProcessedOrders(Long customerId) throws DataAccessException;

    Optional<Order> findById(Long orderId);

    void deleteByID(Long orderId) throws DataAccessException;

    Long placeOrder(Long userId) throws DataAccessException, SQLIntegrityConstraintViolationException;

    Long confirmOrder(Long orderId, OrderDetailsDto orderDetails) throws DataAccessException, SQLIntegrityConstraintViolationException;

    void emptyCart(Long orderId) throws DataAccessException, SQLIntegrityConstraintViolationException;

    int deleteTimedOutOrders() throws DataAccessException;
}
