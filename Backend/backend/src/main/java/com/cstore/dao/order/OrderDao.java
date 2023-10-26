package com.cstore.dao.order;

import com.cstore.dto.order.OrderDetailsDto;
import com.cstore.model.order.Order;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findAll();

    Optional<Order> findById(Long orderId);

    void deleteAll() throws SQLException;

    void deleteByID(Long orderId) throws DataAccessException;

    Long placeOrder(Long userId) throws DataAccessException, SQLIntegrityConstraintViolationException;

    Long confirmOrder(Long orderId, OrderDetailsDto orderDetails) throws DataAccessException, SQLIntegrityConstraintViolationException;

    void emptyCart(Long orderId) throws DataAccessException, SQLIntegrityConstraintViolationException;
}
