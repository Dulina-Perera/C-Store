package com.cstore.domain.report;

import com.cstore.dao.order.OrderDao;
import com.cstore.dao.product.ProductDao;
import com.cstore.dao.property.PropertyDao;
import com.cstore.dao.report.ReportDao;
import com.cstore.dao.variant.VariantDao;
import com.cstore.model.order.Order;
import com.cstore.model.order.OrderItem;
import com.cstore.model.product.Property;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ProductDao productDao;
    private final PropertyDao propertyDao;
    private final OrderDao orderDao;
    private final ReportDao reportDao;

    public String customerOrderReport(
        Long customerId,
        String reportFormat
    ) throws FileNotFoundException, JRException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String path = "/media/dulina/SP PHD U3/Education/Campus Documents/5. Semester 3/Database Systems/C Store" +
                "/Backend/backend/src/main/resources/static/reports/Customer-Order";

        List<Order> processedOrders = orderDao.findProcessedOrders(customerId);

        File generated;
        File file = ResourceUtils.getFile("classpath:templates/Customer-Order Report.jrxml");
        JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(processedOrders);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("created by", "C-Store");

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            generated = new File("%s/html/%d-%s.html".formatted(path, customerId, dateFormat.format(new Date())));
            JasperExportManager.exportReportToHtmlFile(
                jasperPrint,
                generated.getAbsolutePath());
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            generated = new File("%s/pdf/%d-%s.pdf".formatted(path, customerId, dateFormat.format(new Date())));
            JasperExportManager.exportReportToPdfFile(
                jasperPrint,
                generated.getAbsolutePath());
        }
        return "Report generated successfully.";
    }

    @Scheduled(cron = "00 00 00 01 1,4,7,10 *")
    public void quarterlySalesReport() {

    }

    public CustomerOrderReport getCustomerOrderReport(
        Long customerId
    ) {
        List<Order> orders = orderDao.findProcessingAndProcessedOrders(customerId);
        List<ReportItem> reportItems = new ArrayList<>();

        for (Order order : orders) {
            ReportItem reportItem = ReportItem
                .builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .date(order.getDate())
                .totalPayment(order.getTotalPayment())
                .paymentMethod(order.getPaymentMethod())
                .deliveryMethod(order.getDeliveryMethod())
                .build();

            ShippingAddress shippingAddress = ShippingAddress
                .builder()
                .streetNumber(order.getStreetNumber())
                .streetName(order.getStreetName())
                .city(order.getCity())
                .zipcode(order.getZipcode())
                .build();

            reportItem.setShippingAddress(shippingAddress);

            List<OrderItem> orderItems = orderDao.findOrderItems(order.getOrderId());
            List<ReportVariant> variants = new ArrayList<>();

            for (OrderItem orderItem : orderItems) {
                ReportVariant reportVariant = ReportVariant
                    .builder()
                    .productName(productDao.findByVariantId(orderItem.getVariantId()).get().getProductName())
                    .quantity(orderItem.getCount())
                    .price(orderItem.getPrice())
                    .build();

                List<Property> properties = propertyDao.findByVariantId(orderItem.getVariantId());

                Map<String, String> propertyMap = new HashMap<String, String>();
                for (Property property : properties) {
                    propertyMap.put(property.getPropertyName(), property.getValue());
                }

                reportVariant.setProperties(propertyMap);
                variants.add(reportVariant);
            }

            reportItem.setVariants(variants);

            reportItems.add(reportItem);
        }

        return CustomerOrderReport
            .builder()
            .orderItems(reportItems)
            .build();
    }
}
