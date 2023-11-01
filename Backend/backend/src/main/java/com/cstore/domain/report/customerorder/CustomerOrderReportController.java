package com.cstore.domain.report.customerorder;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reg-cust/reports/customer-order")
@RequiredArgsConstructor
public class CustomerOrderReportController {
    private final CustomerOrderReportService customerOrderReportService;

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/{format}/{customer_id}"
    )
    public String customerOrderReport(
        @PathVariable(name = "customer_id", required = true)
        Long customerId,
        @PathVariable(name = "format", required = true)
        String format
    ) throws JRException, FileNotFoundException {
        return customerOrderReportService.customerOrderReport(customerId, format);
    }

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/{customer_id}"
    )
    public ResponseEntity<List<ReportItem>> getCustomerOrderReport(
        @PathVariable(name = "customer_id", required = true)
        Long customerId
    ) {
        try {
            return new ResponseEntity<>(
                customerOrderReportService.getCustomerOrderReport(customerId),
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
