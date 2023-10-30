package com.cstore.domain.report.customerorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class CustomerOrderReport {
    private List<ReportItem> orderItems;
}
