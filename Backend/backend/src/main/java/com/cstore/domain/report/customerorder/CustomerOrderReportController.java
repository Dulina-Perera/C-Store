package com.cstore.domain.report.customerorder;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/reports/customer-order")
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
    public ResponseEntity<CustomerOrderReport> getCustomerOrderReport(
        @PathVariable(name = "customer_id", required = true)
        Long customerId
    ) {
        return ResponseEntity.ok(customerOrderReportService.getCustomerOrderReport(customerId));
    }


}
