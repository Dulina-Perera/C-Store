package com.cstore.service;

import com.cstore.dao.order.OrderDao;
import com.cstore.dao.ordercontact.OrderContactDao;
import com.cstore.dto.OrderDTO;
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

@Service
public class OrderService {
    private final OrderContactDao orderContactDao;
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderContactDao orderContactDao, OrderDao orderDao) {
        this.orderContactDao = orderContactDao;
        this.orderDao = orderDao;
    }

    private OrderDTO convert(Order order) {
        List<OrderContact> orderContacts = orderContactDao.findByOrder(order);
        OrderDTO orderDTO = new OrderDTO();
        List<Integer> telephoneNumbers = new ArrayList<>();

        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setDate(order.getDate());
        orderDTO.setTotalPayment(order.getTotalPayment());
        orderDTO.setPaymentMethod(order.getPaymentMethod());
        orderDTO.setDeliveryMethod(order.getDeliveryMethod());
        orderDTO.setEmail(order.getEmail());
        orderDTO.setStreetNumber(order.getStreetNumber());
        orderDTO.setStreetName(order.getStreetName());
        orderDTO.setCity(order.getCity());
        orderDTO.setZipCode(order.getZipcode());

        for (OrderContact orderContact : orderContacts) {
            telephoneNumbers.add(orderContact.getOrderContactId().getTelephoneNumber());
        }
        orderDTO.setTelephoneNumbers(telephoneNumbers);

        return orderDTO;
    }

    public List<OrderDTO> getAllOrders() {
        return orderDao.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long orderId) {
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
    }
}
