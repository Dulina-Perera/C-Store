package com.cstore.domain.report.quarterlysales;

import com.cstore.dao.product.ProductDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dao.report.ReportDao;
import com.cstore.model.product.Property;
import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuarterlySalesReportService {
    private final ProductDao productDao;
    private final PropertyDao propertyDao;
    private final ReportDao reportDao;

    public Body getSalesReport(
        Short year,
        Short quarter
    ) throws DataAccessException {
        Optional<SalesReport> salesReport = reportDao.findSalesReport(year, quarter);

        if (salesReport.isEmpty()) {
            throw new NoSuchQuarterException("Invalid quarter!");
        }
        Body body = Body
            .builder()
            .year(year)
            .quarter(quarter)
            .totalSales(salesReport.get().getTotalSales())
            .totalEarnings(salesReport.get().getTotalEarnings())
            .variants(new ArrayList<>())
            .build();

        List<SalesItem> salesItems = reportDao.findSalesItems(year, quarter);

        for (SalesItem salesItem : salesItems) {
            Variant variant = Variant
                .builder()
                .productName(productDao.findByVariantId(salesItem.getVariantId()).get().getProductName())
                .sales(salesItem.getSales())
                .earnings(salesItem.getEarnings())
                .build();

            List<Property> properties = propertyDao.findByVariantId(salesItem.getVariantId());

            Map<String, String> propertyMap = new HashMap<String, String>();
            for (Property property : properties) {
                propertyMap.put(property.getPropertyName(), property.getValue());
            }

            variant.setProperties(propertyMap);

            body.getVariants().add(variant);
        }

        return body;
    }

    @Scheduled(cron = "00 00 00 01 1,4,7,10 *")
    public void quarterlySalesReport() {
        LocalDate now = LocalDate.now();

        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int quarterStartMonth = ((currentMonth - 1) / 3) * 3 + 1;

        if (currentMonth == 1) {
            currentYear--;
            quarterStartMonth = 10;
        }

        LocalDate quarterStartDate = LocalDate.of(currentYear, quarterStartMonth, 1);
        Timestamp quarterStart = Timestamp.valueOf(quarterStartDate.atStartOfDay());

        LocalDate quarterEndDate = LocalDate.of(currentYear, quarterStartMonth + 2, 31);
        Timestamp quarterEnd = Timestamp.valueOf(LocalDateTime.of(quarterEndDate, LocalDateTime.MAX.toLocalTime()));

        reportDao.saveQuarterlySalesReport(quarterStart, quarterEnd);
    }
}
