package com.cstore.domain.order.checkout;

import com.cstore.exception.sparsestocks.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/reg-cust/orders/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @RequestMapping(
        method = RequestMethod.POST,
        path = "/{user_id}"
    )
    public Long placeOrder(
        @PathVariable(
            name = "user_id",
            required = true
        )
        Long userId
    ) throws DataAccessException, SparseStocksException, SQLIntegrityConstraintViolationException {
        return checkoutService.placeOrder(userId);
    }
}
