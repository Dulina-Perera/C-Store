package com.cstore.domain.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/all/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;

    @RequestMapping(
        method = RequestMethod.GET,
        path = {
            "/products/sales/max/{period}/{limit}",
            "/products/sales/max/{period}"}
    )
    public ResponseEntity<List<Product>> getProductsWithMostSales(
        @PathVariable(
            name = "period",
            required = true
        )
        Period period,
        @PathVariable(
            name = "limit",
            required = false
        )
        Short limit
    ) {
        log.info("{}", limit);
        limit = limit == null ? 5 : limit;

        try {
            return new ResponseEntity<>(
                reportService.getProductsWithMostSales(period, limit),
                HttpStatus.OK
            );
        } catch (DataAccessException dae) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/categories/orders/max"
    )
    public ResponseEntity<List<Category>> getCategoriesWithMostOrders(
    ) {
        try {
            return new ResponseEntity<>(
                reportService.getCategoriesWithMostOrders(),
                HttpStatus.OK
            );
        } catch (DataAccessException dae) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/quarters/max/{product_id}"
    )
   public Interest getQuartersWithMostInterest(
    @PathVariable(
        name = "product_id",
        required = true
    ) Long productId
   ) {
        return reportService.getQuartersWithMostInterest(productId);
    }
}
