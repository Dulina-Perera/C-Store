package com.cstore.domain.order;

import com.cstore.dao.order.OrderDao;
import com.cstore.dto.order.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;

    public Long confirmOrder(
        Long orderId,
        OrderDetailsDto orderDetails
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        orderDao.emptyCart(orderId);
        orderId = orderDao.confirmOrder(orderId, orderDetails);

        return orderId;
    }

    @Scheduled(cron = "00 00,30 * * * *")
    public void deleteTimedOutOrders(
    ) {
        orderDao.deleteTimedOutOrders();
    }
}
