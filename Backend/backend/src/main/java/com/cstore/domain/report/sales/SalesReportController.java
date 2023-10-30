package com.cstore.domain.report.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports/quarterly-sales")
@RequiredArgsConstructor
public class SalesReportController {
    private final SalesReportService salesReportService;

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/{year}/{quarter}"
    )
    public ResponseEntity<Body> getSalesReport(
        @PathVariable(
            name = "year",
            required = true
        )
        Short year,
        @PathVariable(
            name = "quarter",
            required = true
        )
        Short quadrant
    ) {
        try {
            return new ResponseEntity<>(
                salesReportService.getSalesReport(year, quadrant),
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
