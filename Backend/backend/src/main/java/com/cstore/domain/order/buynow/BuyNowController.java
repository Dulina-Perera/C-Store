package com.cstore.domain.order.buynow;

import com.cstore.dto.VariantProperiesDto;
import com.cstore.exception.NoSuchVariantException;
import com.cstore.exception.sparsestocks.SparseStocksException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/cust/orders/buy-now")
public class BuyNowController {
    private final BuyNowService buyNowService;

    @RequestMapping(
        method = RequestMethod.POST,
        path = {"/{user_id}", ""}
    )
    public ResponseEntity<Long> placeOrder(
        @PathVariable(
            name = "user_id",
            required = false
        )
        Long userId,
        @RequestBody(
            required = true
        )
        VariantProperiesDto properties
    ) throws DataAccessException, NoSuchVariantException, SparseStocksException {
        try {
            return new ResponseEntity<>(
                buyNowService.buyNow(
                    userId,
                    properties
                ),
                HttpStatus.OK
            );
        } catch (NoSuchVariantException nsve) {
            return new ResponseEntity<>(
                null,
                HttpStatus.BAD_REQUEST
            );
        } catch (SparseStocksException sse) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
