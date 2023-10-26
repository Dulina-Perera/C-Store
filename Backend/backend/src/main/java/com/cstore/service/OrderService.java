package com.cstore.service;

import com.cstore.dao.order.OrderDao;
import com.cstore.dao.order.contact.OrderContactDao;
import com.cstore.dto.OrderDto;
import com.cstore.exception.NoSuchOrderException;
import com.cstore.model.order.Order;
import com.cstore.model.order.OrderContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
public class OrderService {
    /*private final OrderContactDao orderContactDao;
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderContactDao orderContactDao, OrderDao orderDao) {
        this.orderContactDao = orderContactDao;
        this.orderDao = orderDao;
    }

    private OrderDto convert(Order order) {
        List<OrderContact> orderContacts = orderContactDao.findByOrder(order);
        List<Integer> telephoneNumbers = new ArrayList<>();

        OrderDto orderDto = OrderDto
            .builder()
            .orderId(order.getOrderId())
            .date(order.getDate())
            .totalPayment(order.getTotalPayment())
            .paymentMethod(order.getPaymentMethod())
            .deliveryMethod(order.getDeliveryMethod())
            .email(order.getEmail())
            .streetNumber(order.getStreetNumber())
            .streetName(order.getStreetName())
            .city(order.getCity())
            .zipCode(order.getZipcode())
            .build();

        for (OrderContact orderContact : orderContacts) {
            telephoneNumbers.add(orderContact.getOrderContactId().getTelephoneNumber());
        }
        orderDto.setTelephoneNumbers(telephoneNumbers);

        return orderDto;
    }

    public List<OrderDto> getAllOrders() {
        return orderDao.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long orderId) {
        Optional<Order> order = orderDao.findById(orderId);

        if (order.isEmpty()) {
            throw new NoSuchOrderException("Order with id " + orderId + " not found.");
        } else {
            return convert(order.get());
        }
    }

    public void deleteAllOrders() throws SQLException {
        orderDao.deleteAll();
    }

    public void deleteOrderByID(Long orderId) throws SQLException {
        orderDao.deleteByID(orderId);
    }*/
}
