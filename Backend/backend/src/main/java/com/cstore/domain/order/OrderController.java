package com.cstore.domain.order;

import com.cstore.dto.order.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/reg-cust/orders")
public class OrderController {
    private final OrderService orderService;

    @RequestMapping(
        method = RequestMethod.POST,
        path = "/confirm/{order_id}"
    )
    public ModifiedOrder confirmOrder(
        @PathVariable(
            name = "order_id",
            required = true
        )
        Long orderId,
        @RequestBody(required = true)
        OrderDetailsDto dto
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        return orderService.confirmOrder(orderId, dto);
    }
}

