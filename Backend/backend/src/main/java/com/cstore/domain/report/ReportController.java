package com.cstore.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/sales/products/max/{period}"
    )
    public ResponseEntity<List<Product>> getProductsWithMostSales(
        @PathVariable(
            name = "period",
            required = true
        )
        Period period
    ) {
        try {
            return new ResponseEntity<>(
                reportService.getProductsWithMostSales(period),
                HttpStatus.OK
            );
        } catch (DataAccessException dae) {
            return new ResponseEntity<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
