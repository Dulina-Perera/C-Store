package com.cstore.controller;

import com.cstore.dto.OrderDto;
import com.cstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{order_id}")
    public OrderDto getOrderById(@PathVariable(name = "order_id", required = true) Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "")
    public void deleteAllOrders() throws SQLException {
        orderService.deleteAllOrders();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{order_id}")
    public void deleteOrderById(@PathVariable(name = "order_id", required = true) Long orderId) throws SQLException {
        orderService.deleteOrderByID(orderId);
    }

    /*@RequestMapping(method = RequestMethod.POST, path = "/orders")
    public Order addOrderDetails(@PathVariable(name = "customer_id", required = true) Long customerId) {
        *//* TODO
         *  Check whether the cart is empty.
         *  If so, throw an error saying that an order of no items cannot be placed.
         *
         * TODO
         *  Check whether all the required details are provided.
         *  If not, throw an error indicating that those details are required.
         *
         * TODO
         *  Create a new empty order.
         *  Ask the frontend to process the payment & call 'placeOrder'.
         *//*
        return orderService.addOrderDetails(customerId);
    }

    *//* This method is called immediately after the success of an order. *//*
    @RequestMapping(method = RequestMethod.PATCH, path = "/orders/{order_id}")
    public Order placeOrder(@PathVariable(name = "customer_id", required = true) Long customerId,
                            @PathVariable(name = "order_id", required = true) Long orderId,
                            @RequestBody(required = true)Map<String, Object> orderDetails) {
        *//* TODO
         *  Update the order details, copy all the cart items from the cart to the order & empty the cart.
         *//*
        return orderService.placeOrder(customerId, orderId, orderDetails);
    }*/
}
