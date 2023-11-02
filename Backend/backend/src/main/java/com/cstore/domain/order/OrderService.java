package com.cstore.domain.order;

import com.cstore.dao.order.OrderDao;
import com.cstore.dto.order.OrderDetailsDto;
import com.cstore.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;

    public ModifiedOrder confirmOrder(
        Long orderId,
        OrderDetailsDto orderDetails
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        String[] cities = {"Colombo", "Trincomalee", "Anuradhapura", "Negombo", "Vacuniya", "Nuwara Eliya", "Gampaha",
        "Polonnaruwa", "Kalutara", "Kandy", "Jaffna", "Ratnapura", "Badulla", "Matale", "Mannar", "Chilaw", "Ampara",
        "Kegalle", "Batticaloa", "Kurunegala", "Matara", "Hambantota"};

        List<String> mainCities = Arrays.asList(cities);

        orderDao.emptyCart(orderId);
        orderId = orderDao.confirmOrder(orderId, orderDetails);

        Order order = orderDao.findById(orderId).get();

        ModifiedOrder modifiedOrder = ModifiedOrder
            .builder()
            .orderId(order.getOrderId())
            .status(order.getStatus())
            .date(order.getDate())
            .totalPayment(order.getTotalPayment())
            .paymentMethod(order.getPaymentMethod())
            .deliveryMethod(order.getDeliveryMethod())
            .email(order.getEmail())
            .streetNumber(order.getStreetNumber())
            .streetName(order.getStreetName())
            .city(order.getCity())
            .zipcode(order.getZipcode())
            .build();


        if (mainCities.contains(order.getCity())) {
            modifiedOrder.setDeliveryEstimate(Date.from(
                LocalDate.now().plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant()
            ));
        } else {
            modifiedOrder.setDeliveryEstimate(Date.from(
                LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()
            ));
        }

        return modifiedOrder;
    }

    @Scheduled(cron = "00 00,30 * * * *")
    public void deleteTimedOutOrders(
    ) {
        orderDao.deleteTimedOutOrders();
    }
}
