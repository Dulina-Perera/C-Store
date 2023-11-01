package com.cstore.domain.order.buynow;

import com.cstore.dto.VariantProperiesDto;
import com.cstore.exception.sparsestocks.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders/buy-now")
public class BuyNowController {
    private final BuyNowService buyNowService;

    @RequestMapping(
        method = RequestMethod.POST,
        path = "/{user_id}"
    )
    public Long placeOrder(
        @PathVariable(
            name = "user_id",
            required = true
        )
        Long userId,
        @RequestBody(
            required = true
        )
        VariantProperiesDto properies
    ) throws DataAccessException, SparseStocksException, SQLIntegrityConstraintViolationException {
        return buyNowService.buyNow(
            userId,
            properies
        );
    }
}
