package com.cstore.dao.order;

import com.cstore.domain.report.customerorder.OrderItemProperty;
import com.cstore.dto.order.OrderDetailsDto;
import com.cstore.model.order.Order;
import com.cstore.model.order.OrderItem;
import org.springframework.dao.DataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findAll();

    List<OrderItem> findOrderItems(Long orderId) throws DataAccessException;

    List<OrderItemProperty> findAllOrderItems(Long orderId) throws DataAccessException;

    List<Order> findProcessedOrders(Long customerId) throws DataAccessException;

    List<Order> findProcessingAndProcessedOrders(Long customerId) throws DataAccessException;

    Optional<Order> findById(Long orderId);

    void deleteByID(Long orderId) throws DataAccessException;

    Long placeOrder(Long userId) throws DataAccessException, SQLIntegrityConstraintViolationException;

    Long confirmOrder(Long orderId, OrderDetailsDto orderDetails) throws DataAccessException, SQLIntegrityConstraintViolationException;

    void emptyCart(Long orderId) throws DataAccessException, SQLIntegrityConstraintViolationException;

    int deleteTimedOutOrders() throws DataAccessException;
}
