package com.cstore.domain.order.checkout;

import com.cstore.exception.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders")
public class CheckoutController {
    private final CheckoutService serv;

    @RequestMapping(
        method = RequestMethod.POST,
        path = "/place/{user_id}"
    )
    public Long placeOrder(
        @PathVariable(name = "user_id", required = true)
        Long userId
    ) throws DataAccessException, SparseStocksException, SQLIntegrityConstraintViolationException {
        return serv.placeOrder(userId);
    }
}
