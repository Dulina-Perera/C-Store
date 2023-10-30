package com.cstore.domain.report.sales;

import com.cstore.dao.product.ProductDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dao.report.ReportDao;
import com.cstore.model.product.Property;
import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SalesReportService {
    private final ProductDao productDao;
    private final PropertyDao propertyDao;
    private final ReportDao reportDao;

    public Body getSalesReport(
        Short year,
        Short quarter
    ) throws DataAccessException {
        Body body;
        Optional<SalesReport> salesReport = reportDao.findSalesReport(year, quarter);

        if (salesReport.isEmpty()) {
            throw new NoSuchQuarterException("Invalid quarter!");
        }
        body = Body
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
}
